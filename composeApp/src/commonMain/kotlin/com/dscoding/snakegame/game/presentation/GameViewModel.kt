package com.dscoding.snakegame.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.domain.GameCoordinator
import com.dscoding.snakegame.core.domain.models.ControlMode
import com.dscoding.snakegame.core.presentation.util.UiText
import com.dscoding.snakegame.game.domain.engine.models.GameEndReason
import com.dscoding.snakegame.game.presentation.models.PausedState
import com.dscoding.snakegame.game.presentation.models.PlayState
import com.dscoding.snakegame.game.presentation.utils.PlatformShareSheet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.game_over
import snakegame.composeapp.generated.resources.victory

class GameViewModel(
    private val gamePreferences: GamePreferences,
    private val gameCoordinator: GameCoordinator,
    private val shareSheet: PlatformShareSheet
) : ViewModel() {

    companion object {
        const val BOARD_SIZE = 16
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
                        score = 0,
                        highScoreAtGameEnd = null,
                    )
                }
                runSnakeGame()
            }

            GameAction.OnResumeGameClick -> {
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
                if (state.value.currentPlayState is PlayState.Finished) return

                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Paused(PausedState.MENU),
                        countdownSecondsRemaining = null
                    )
                }
                gameCoordinator.pauseGame()
            }

            GameAction.OnSettingsClick -> {
                if (state.value.currentPlayState is PlayState.Finished) return

                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Paused(PausedState.SETTINGS),
                    )
                }
                gameCoordinator.pauseGame()
            }

            GameAction.OnSettingsDialogDismiss -> {
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

            GameAction.OnFinishedDialogDismiss -> {
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.ReadyToPlay,
                        score = 0
                    )
                }
            }

            is GameAction.OnShareResultClick -> {
                shareSheet.shareText(
                    text = action.shareMessage
                )
            }

            GameAction.OnInvalidAppState -> {
                val shouldPause =
                    state.value.currentPlayState is PlayState.Playing
                            || state.value.currentPlayState == PlayState.Paused(PausedState.COUNTDOWN)
                if (shouldPause) {
                    _state.update {
                        it.copy(
                            currentPlayState = PlayState.Paused(PausedState.MENU),
                            countdownSecondsRemaining = null,
                        )
                    }
                    gameCoordinator.pauseGame()
                }
            }

            else -> Unit
        }
    }

    private fun runSnakeGame() {
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
            onGameEnded = { endResult ->
                val gameEndedMessage = when (endResult.reason) {
                    GameEndReason.Victory -> UiText.Resource(Res.string.victory)
                    else -> UiText.Resource(Res.string.game_over)
                }
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.Finished,
                        highScoreAtGameEnd = it.highScore,
                        gameEndMessage = gameEndedMessage
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