package com.dscoding.snakegame.game.domain.models

enum class MovementDirection(val delta: Pair<Int, Int>) {
    UP(0 to -1),
    DOWN(0 to 1),
    LEFT(-1 to 0),
    RIGHT(1 to 0)
}