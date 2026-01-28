package com.dscoding.snakegame.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.game.presentation.models.Direction
import com.dscoding.snakegame.game.presentation.models.PlayState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile
import kotlin.random.Random

class GameViewModel : ViewModel() {

    companion object {
        const val BOARD_SIZE = 24
        const val TICK_SPEED = 180L
        const val SNAKE_START_LENGTH = 3
    }

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(GameState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                initializeGameLogic()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = GameState()
        )

    @Volatile
    private var nextMove: Pair<Int, Int> = 1 to 0

    fun initializeGameLogic() {
        viewModelScope.launch {
            var snakeLength = SNAKE_START_LENGTH

            _state.update {
                it.copy(
                    food = Random.nextInt(BOARD_SIZE) to Random.nextInt(BOARD_SIZE),
                    snake = listOf(7 to 7)
                )
            }

            while (state.value.currentPlayState == PlayState.PLAYING) {
                delay(TICK_SPEED)

                val move = nextMove

                val currentSnakeHeadPosition = state.value.snake.first()
                val newSnakeHeadPosition = Pair(
                    (currentSnakeHeadPosition.first + move.first + BOARD_SIZE) % BOARD_SIZE,
                    (currentSnakeHeadPosition.second + move.second + BOARD_SIZE) % BOARD_SIZE
                )

                val snakeAteFood = newSnakeHeadPosition == state.value.food
                val snakeHitItself = state.value.snake.contains(newSnakeHeadPosition)

                if (snakeAteFood) snakeLength++
                if (snakeHitItself) {
                    _state.update {
                        it.copy(
                            currentPlayState = PlayState.FINISHED
                        )
                        return@launch
                    }
                }

                _state.update {
                    it.copy(
                        food = if (snakeAteFood) {
                            Random.nextInt(BOARD_SIZE) to Random.nextInt(BOARD_SIZE)
                        } else state.value.food,
                        snake = listOf(newSnakeHeadPosition) + state.value.snake.take(snakeLength - 1)
                    )
                }
            }
        }
    }

    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.OnDirectionChanged -> {
                val requestedMove = when (action.direction) {
                    Direction.UP -> 0 to -1
                    Direction.DOWN -> 0 to 1
                    Direction.LEFT -> -1 to 0
                    Direction.RIGHT -> 1 to 0
                }
                setNextMoveIfAllowed(requestedMove)
            }

            GameAction.OnGamePaused -> {
                _state.update { it.copy(currentPlayState = PlayState.PAUSED) }
            }

            GameAction.OnGameStarted -> {
                _state.update { it.copy(currentPlayState = PlayState.PLAYING) }
            }
        }
    }

    private fun setNextMoveIfAllowed(requested: Pair<Int, Int>) {
        val isReverse = requested.first == -nextMove.first && requested.second == -nextMove.second
        if (!isReverse) {
            nextMove = requested
        }
    }
}