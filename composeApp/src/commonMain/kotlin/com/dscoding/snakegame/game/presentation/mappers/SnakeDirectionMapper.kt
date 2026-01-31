package com.dscoding.snakegame.game.presentation.mappers

import com.dscoding.snakegame.game.domain.models.MovementInput
import com.dscoding.snakegame.game.presentation.models.SnakeDirection

fun SnakeDirection.toMovementInput(): MovementInput {
    return when (this) {
        SnakeDirection.UP -> MovementInput.UP
        SnakeDirection.DOWN -> MovementInput.DOWN
        SnakeDirection.LEFT -> MovementInput.LEFT
        SnakeDirection.RIGHT -> MovementInput.RIGHT
    }
}