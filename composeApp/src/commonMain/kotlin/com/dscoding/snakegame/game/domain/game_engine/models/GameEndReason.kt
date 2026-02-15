package com.dscoding.snakegame.game.domain.game_engine.models

sealed interface GameEndReason {
    data object HitSelf : GameEndReason
    data object Victory : GameEndReason
    data object Unknown : GameEndReason
}