package com.dscoding.snakegame.game.presentation

import com.dscoding.snakegame.game.domain.engine.models.MovementDirection

sealed interface GameAction {
    data class OnDirectionClick(val movementDirection: MovementDirection) : GameAction
    data object OnGameStarted : GameAction
    data object OnGameResumed : GameAction
    data object OnGameRestarted : GameAction
    data object OnGamePaused : GameAction
}