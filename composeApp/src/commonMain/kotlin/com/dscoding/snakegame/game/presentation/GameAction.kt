package com.dscoding.snakegame.game.presentation

import com.dscoding.snakegame.game.domain.engine.models.MovementDirection

sealed interface GameAction {
    data class OnDirectionClick(val movementDirection: MovementDirection) : GameAction
    data object OnGamePaused : GameAction
    data object OnGameStarted : GameAction
}