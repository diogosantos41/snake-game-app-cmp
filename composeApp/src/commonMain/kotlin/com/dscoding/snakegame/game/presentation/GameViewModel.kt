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
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class GameViewModel : ViewModel() {

    companion object {
        const val BOARD_SIZE = 20
        const val TICK_SPEED = 140L
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

    private val mutex = Mutex()

    private var nextMove = Pair(1, 0)
        set(value) {
            viewModelScope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    fun initializeGameLogic() {
        viewModelScope.launch {
            var snakeLength = SNAKE_START_LENGTH

            _state.update {
                it.copy(
                    food = Pair(
                        Random.nextInt(BOARD_SIZE),
                        Random.nextInt(BOARD_SIZE)
                    ),
                    snake = listOf(Pair(7, 7))
                )
            }

            while (state.value.currentPlayState == PlayState.PLAYING) {
                delay(TICK_SPEED)
                _state.update { it ->
                    val newSnakeHeadPosition = it.snake.first().let { currentSnakeHeadPosition ->
                        mutex.withLock {
                            Pair(
                                (currentSnakeHeadPosition.first + nextMove.first + BOARD_SIZE) % BOARD_SIZE,
                                (currentSnakeHeadPosition.second + nextMove.second + BOARD_SIZE) % BOARD_SIZE
                            )
                        }
                    }

                    val snakeAteFood = newSnakeHeadPosition == it.food
                    val snakeHitItself = it.snake.contains(newSnakeHeadPosition)

                    if (snakeAteFood) {
                        snakeLength++
                    }

                    if (snakeHitItself) {
                        _state.update {
                            it.copy(
                                currentPlayState = PlayState.FINISHED
                            )
                        }
                        return@launch
                    }

                    it.copy(
                        food = if (snakeAteFood) Pair(
                            Random.nextInt(BOARD_SIZE),
                            Random.nextInt(BOARD_SIZE)
                        ) else it.food,
                        snake = listOf(newSnakeHeadPosition) + it.snake.take(snakeLength - 1)
                    )
                }
            }
        }
    }

    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.OnDirectionChanged -> {
                nextMove = when (action.direction) {
                    Direction.UP -> 0 to -1
                    Direction.DOWN -> 0 to 1
                    Direction.LEFT -> -1 to 0
                    Direction.RIGHT -> 1 to 0
                }
            }

            GameAction.OnGamePaused -> {
                _state.update { it.copy(currentPlayState = PlayState.PAUSED) }
            }

            GameAction.OnGameStarted -> {
                _state.update { it.copy(currentPlayState = PlayState.PLAYING) }
            }
        }
    }

}