package com.dscoding.snakegame.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.domain.GameCoordinator
import com.dscoding.snakegame.game.presentation.models.ControlMode
import com.dscoding.snakegame.game.presentation.models.PausedState
import com.dscoding.snakegame.game.presentation.models.PlayState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val gamePreferences: GamePreferences,
    private val gameCoordinator: GameCoordinator
) : ViewModel() {

    companion object {
        const val BOARD_SIZE = 14
    }

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(GameState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeGamePreferences()
                hasLoadedInitialData = true

            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = GameState()
        )

    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.OnDirectionClick -> {
                gameCoordinator.requestDirectionChange(action.movementDirection)
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
                gameCoordinator.resumeWithCountdown(
                    scope = viewModelScope,
                    onCountdown = { secondsRemaining ->
                        _state.update {
                            it.copy(
                                countdownSecondsRemaining = secondsRemaining
                            )
                        }
                    },
                    onResumed = {
                        _state.update {
                            it.copy(
                                currentPlayState = PlayState.Playing,
                            )
                        }
                    }
                )
            }

            GameAction.OnPauseGameClick -> {
                // TODO [BUG] If I pause right after loosing, the games ends but still becomes paused.
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Paused(PausedState.MENU),
                    )
                }
                gameCoordinator.pauseGame()
            }

            GameAction.OnSettingsClick -> {
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Paused(PausedState.SETTINGS),
                    )
                }
                if (gameCoordinator.isGameInProgress()) gameCoordinator.pauseGame()
            }

            GameAction.OnSettingsDismissClick -> {
                if (gameCoordinator.isGameInProgress()) {
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
        gameCoordinator.startNewGame(
            scope = viewModelScope,
            boardSize = BOARD_SIZE,
            onTick = { tick ->
                _state.update {
                    it.copy(
                        food = tick.food,
                        snake = tick.snake,
                        score = it.score + if (tick.ateFood) 1 else 0,
                        currentMovementDirection = tick.movementDirection
                    )
                }
            },
            onGameEnded = {
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Finished
                    )
                }
                saveHighscore()
            }
        )
    }

    private fun saveHighscore() {
        viewModelScope.launch {
            val finalScore = _state.value.score
            val currentHigh = state.value.highScore
            if (finalScore > currentHigh) gamePreferences.setHighscore(finalScore)
        }
    }

    private fun observeGamePreferences() {
        combine(
            gamePreferences.observeHighscore(),
            gamePreferences.observeControlMode(),
        ) { highScore, controlMode ->
            _state.update {
                it.copy(
                    highScore = highScore,
                    movementControlMode = ControlMode.valueOf(controlMode.name)
                )
            }
        }.launchIn(viewModelScope)
    }
}