package com.dscoding.snakegame.game.presentation

import com.dscoding.snakegame.game.presentation.models.Direction
import com.dscoding.snakegame.game.presentation.models.PlayState

data class GameState(
    val currentDirection: Direction = Direction.LEFT,
    val currentPlayState: PlayState = PlayState.PLAYING,
    val score: Int = 0,
    val food: Pair<Int, Int>? = null,
    val snake: List<Pair<Int, Int>> = emptyList()
)