package com.dscoding.snakegame.game.data.engine.mappers

import com.dscoding.snakegame.game.domain.game_engine.models.MovementDirection

fun Pair<Int, Int>.toMovementDirection(): MovementDirection =
    when (this) {
        MovementDirection.UP.delta -> MovementDirection.UP
        MovementDirection.DOWN.delta -> MovementDirection.DOWN
        MovementDirection.LEFT.delta -> MovementDirection.LEFT
        MovementDirection.RIGHT.delta -> MovementDirection.RIGHT
        else -> throw IllegalArgumentException("Invalid Direction")
    }