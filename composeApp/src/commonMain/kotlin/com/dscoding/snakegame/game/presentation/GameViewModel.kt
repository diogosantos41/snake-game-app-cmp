package com.dscoding.snakegame.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.game.domain.GameEngine
import com.dscoding.snakegame.game.domain.models.onGameEnded
import com.dscoding.snakegame.game.domain.models.onTick
import com.dscoding.snakegame.game.presentation.mappers.toMovementInput
import com.dscoding.snakegame.game.presentation.models.PlayState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class GameViewModel(private val gameEngine: GameEngine) : ViewModel() {

    companion object {
        const val BOARD_SIZE = 14
    }

    private var hasLoadedInitialData = false

    private var gameEngineJob: Job? = null

    private val _state = MutableStateFlow(GameState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                //----
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
                val requestedMovementInput = action.snakeDirection.toMovementInput()
                gameEngine.requestDirectionChange(requestedMovementInput)
            }

            GameAction.OnGamePaused -> {
                gameEngine.pauseGame()
                _state.update {
                    it.copy(currentPlayState = PlayState.PAUSED)
                }
            }

            GameAction.OnGameStarted -> {
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.PLAYING,
                        score = 0
                    )
                }
                runSnakeGame()
            }
        }
    }

    private fun runSnakeGame() {
        gameEngineJob?.cancel()
        gameEngineJob = null
        gameEngineJob = gameEngine
            .runGame(boardSize = BOARD_SIZE)
            .onTick { tick ->
                _state.update {
                    it.copy(
                        food = tick.food,
                        snake = tick.snake,
                        score = it.score + if (tick.ateFood) 1 else 0
                    )
                }
            }.onGameEnded {
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.FINISHED,
                    )
                }
            }.launchIn(viewModelScope)
    }
}