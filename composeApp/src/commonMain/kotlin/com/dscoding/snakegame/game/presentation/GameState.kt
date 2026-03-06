package com.dscoding.snakegame.game.presentation

import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import com.dscoding.snakegame.core.domain.models.ControlMode
import com.dscoding.snakegame.game.presentation.models.PlayState

data class GameState(
    val currentPlayState: PlayState = PlayState.ReadyToPlay,
    val countdownSecondsRemaining: Int? = null,
    val movementControlMode: ControlMode = ControlMode.BUTTONS,
    val score: Int = 0,
    val highScore: Int = 0,
    val highScoreAtGameEnd: Int? = null,
    val food: Pair<Int, Int>? = null,
    val snake: List<Pair<Int, Int>> = emptyList(),
    val currentMovementDirection: MovementDirection = MovementDirection.RIGHT,
)