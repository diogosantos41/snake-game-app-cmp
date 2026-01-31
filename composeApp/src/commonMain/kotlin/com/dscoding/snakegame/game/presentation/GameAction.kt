package com.dscoding.snakegame.game.presentation

import com.dscoding.snakegame.game.presentation.models.SnakeDirection

sealed interface GameAction {
    data class OnDirectionClick(val snakeDirection: SnakeDirection) : GameAction
    data object OnGamePaused : GameAction
    data object OnGameStarted : GameAction
}