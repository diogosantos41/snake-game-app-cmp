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
        const val BOARD_SIZE = 16
        const val TICK_SPEED = 120L
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

    private var move = Pair(1, 0)
        set(value) {
            viewModelScope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    fun initializeGameLogic() {
        viewModelScope.launch {
            var snakeLength = 4

            _state.update {
                it.copy(
                    food = Pair(5, 5),
                    snake = listOf(Pair(7, 7))
                )
            }

            while (state.value.currentPlayState == PlayState.PLAYING) {
                delay(TICK_SPEED)
                _state.update {
                    val newPosition = it.snake.first().let { poz ->
                        mutex.withLock {
                            Pair(
                                (poz.first + move.first + BOARD_SIZE) % BOARD_SIZE,
                                (poz.second + move.second + BOARD_SIZE) % BOARD_SIZE
                            )
                        }
                    }

                    if (newPosition == it.food) {
                        snakeLength++
                    }

                    if (it.snake.contains(newPosition)) {
                        snakeLength = 4
                    }

                    it.copy(
                        food = if (newPosition == it.food) Pair(
                            Random.nextInt(BOARD_SIZE),
                            Random.nextInt(BOARD_SIZE)
                        ) else it.food,
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1)
                    )
                }
            }
        }
    }

    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.OnDirectionChanged -> {
                move = when (action.direction) {
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