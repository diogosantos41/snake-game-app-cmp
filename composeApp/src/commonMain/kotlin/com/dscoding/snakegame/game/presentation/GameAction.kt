package com.dscoding.snakegame.game.presentation

import com.dscoding.snakegame.game.presentation.models.Direction

sealed interface GameAction {
    data class OnDirectionChanged(val direction: Direction) : GameAction
    data object OnGamePaused : GameAction
    data object OnGameStarted : GameAction
}