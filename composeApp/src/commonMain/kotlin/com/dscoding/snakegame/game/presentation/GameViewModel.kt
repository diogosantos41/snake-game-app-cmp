package com.dscoding.snakegame.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.game.domain.GameEngine
import com.dscoding.snakegame.game.domain.models.MovementInput
import com.dscoding.snakegame.game.domain.models.onGameEnded
import com.dscoding.snakegame.game.domain.models.onTick
import com.dscoding.snakegame.game.presentation.mappers.toMovementInput
import com.dscoding.snakegame.game.presentation.models.PlayState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(private val gameEngine: GameEngine) : ViewModel() {

    companion object {
        const val BOARD_SIZE = 16
    }

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(GameState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                runSnakeGame()
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
                gameEngine.resumeGame()
                _state.update { it.copy(currentPlayState = PlayState.PLAYING) }
            }
        }
    }

    private fun runSnakeGame() {
        viewModelScope.launch {
            gameEngine
                .runGame()
                .onTick { tick ->
                    _state.update {
                        it.copy(
                            food = tick.food,
                            snake = tick.snake,
                            score = state.value.score + if (tick.ateFood) 1 else 0
                        )
                    }
                }.onGameEnded {
                    _state.update {
                        it.copy(
                            currentPlayState = PlayState.FINISHED
                        )
                    }
                }.collect()
        }
    }
}