package com.dscoding.snakegame.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.domain.GameEngine
import com.dscoding.snakegame.game.domain.models.onGameEnded
import com.dscoding.snakegame.game.domain.models.onTick
import com.dscoding.snakegame.game.presentation.models.PlayState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameEngine: GameEngine,
    private val gamePreferences: GamePreferences
) : ViewModel() {

    companion object {
        const val BOARD_SIZE = 14
    }

    private var hasLoadedInitialData = false

    private var gameEngineJob: Job? = null

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
                        score = it.score + if (tick.ateFood) 1 else 0,
                        currentMovementDirection = tick.movementDirection
                    )
                }
            }.onGameEnded {
                saveHighscore()
                _state.update {
                    it.copy(
                        currentPlayState = PlayState.FINISHED,
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun saveHighscore() {
        viewModelScope.launch {
            val finalScore = _state.value.score
            val currentHigh = state.value.highScore
            if (finalScore > currentHigh) gamePreferences.setHighscore(finalScore)
        }
    }
}