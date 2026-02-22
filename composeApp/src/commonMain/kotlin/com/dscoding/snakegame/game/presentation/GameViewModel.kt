package com.dscoding.snakegame.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.domain.audio.GameAudio
import com.dscoding.snakegame.game.domain.audio.models.SoundEffect.EAT
import com.dscoding.snakegame.game.domain.audio.models.SoundEffect.GAME_OVER
import com.dscoding.snakegame.game.domain.engine.GameEngine
import com.dscoding.snakegame.game.domain.engine.models.onGameEnded
import com.dscoding.snakegame.game.domain.engine.models.onTick
import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import com.dscoding.snakegame.game.domain.haptics.models.HapticType.HEAVY
import com.dscoding.snakegame.game.domain.haptics.models.HapticType.LIGHT
import com.dscoding.snakegame.game.presentation.models.PausedState
import com.dscoding.snakegame.game.presentation.models.PlayState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameEngine: GameEngine,
    private val gamePreferences: GamePreferences,
    private val gameAudio: GameAudio,
    private val gameHaptics: GameHaptics
) : ViewModel() {

    companion object {
        const val BOARD_SIZE = 14
    }

    private var gameEngineJob: Job? = null

    private var countdownJob: Job? = null

    private val _state = MutableStateFlow(GameState())
    val state = combine(
        _state,
        gamePreferences.observeHighscore()
    ) { currentState, highscore ->
        currentState.copy(
            highScore = highscore
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = GameState()
    )

    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.OnDirectionClick -> {
                gameEngine.requestDirectionChange(action.movementDirection)
            }

            GameAction.OnStartGameClick, GameAction.OnRestartGameClick -> {
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Playing,
                        score = 0
                    )
                }
                runSnakeGame()
            }

            GameAction.OnResumeGameClick -> {
                // TODO [BUG] Pause Dialog Flashes because countdown ends but playState == PAUSE for 0.01 seconds
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Paused(PausedState.COUNTDOWN),
                    )
                }
                startCountdownThen {
                    _state.update {
                        it.copy(
                            currentPlayState = PlayState.Playing,
                        )
                    }
                    gameEngine.resumeGame()
                    gameAudio.startMusic()
                }
            }

            GameAction.OnPauseGameClick -> {
                // TODO [BUG] If I pause right after loosing, the games ends but still becomes paused.
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Paused(PausedState.MENU),
                    )
                }
                gameEngine.pauseGame()
                gameAudio.stopMusic()
            }

            GameAction.OnSettingsClick -> {
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Paused(PausedState.SETTINGS),
                    )
                }
                gameEngineJob?.let {
                    gameEngine.pauseGame()
                    gameAudio.stopMusic()
                }
            }
            GameAction.OnSettingsDismissClick -> {
                if(gameEngineJob?.isActive ?: false) {
                    _state.update {
                        it.copy(
                            currentPlayState = PlayState.Paused(PausedState.MENU),
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            currentPlayState = PlayState.ReadyToPlay,
                        )
                    }
                }
            }
        }
    }

    private fun runSnakeGame() {
        // TODO Stop all SFX sounds (and music maybe) before starting the music
        gameAudio.startMusic()
        gameEngineJob?.cancel()
        gameEngineJob = null
        gameEngineJob = gameEngine
            .runGame(boardSize = BOARD_SIZE)
            .onTick { tick ->
                if (tick.ateFood) {
                    ateFoodFeedback()
                }
                _state.update {
                    it.copy(
                        food = tick.food,
                        snake = tick.snake,
                        score = it.score + if (tick.ateFood) 1 else 0,
                        currentMovementDirection = tick.movementDirection
                    )
                }
            }.onGameEnded {
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Finished,
                    )
                }
                gameEndedFeedback()
                saveHighscore()
                gameEngineJob?.cancel()
                gameEngineJob = null

            }.launchIn(viewModelScope)
    }

    private fun ateFoodFeedback() {
        gameAudio.playSoundEffect(EAT)
        gameHaptics.vibrate(LIGHT)
    }

    private fun gameEndedFeedback() {
        gameAudio.stopMusic()
        gameAudio.playSoundEffect(GAME_OVER)
        gameHaptics.vibrate(HEAVY)
    }

    private fun startCountdownThen(actionAfter: () -> Unit) {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            for (t in 3 downTo 1) {
                _state.update { it.copy(countdownSecondsRemaining = t) }
                delay(1_000)
            }
            _state.update { it.copy(countdownSecondsRemaining = null) }
            actionAfter()
        }
    }

    private fun saveHighscore() {
        viewModelScope.launch {
            val finalScore = _state.value.score
            val currentHigh = state.value.highScore
            if (finalScore > currentHigh) gamePreferences.setHighscore(finalScore)
        }
    }
}