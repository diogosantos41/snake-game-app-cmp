package com.dscoding.snakegame.game.domain.models

sealed interface GameEndReason {
    data object HitSelf : GameEndReason
    data object HitWall : GameEndReason
    data object Victory : GameEndReason
    data object Unknown : GameEndReason
}