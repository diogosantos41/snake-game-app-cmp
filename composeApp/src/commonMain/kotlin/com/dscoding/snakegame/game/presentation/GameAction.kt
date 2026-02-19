package com.dscoding.snakegame.game.presentation

import com.dscoding.snakegame.game.domain.engine.models.MovementDirection

sealed interface GameAction {
    data class OnDirectionClick(val movementDirection: MovementDirection) : GameAction
    data object OnStartGameClick : GameAction
    data object OnResumeGameClick : GameAction
    data object OnRestartGameClick : GameAction
    data object OnPauseGameClick : GameAction
    data object OnSettingsClick : GameAction
}