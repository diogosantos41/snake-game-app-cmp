package com.dscoding.snakegame.game.presentation

import com.dscoding.snakegame.game.presentation.models.PlayState

data class GameState(
    val currentPlayState: PlayState = PlayState.READY_TO_PLAY,
    val score: Int = 0,
    val food: Pair<Int, Int>? = null,
    val snake: List<Pair<Int, Int>> = emptyList(),
)