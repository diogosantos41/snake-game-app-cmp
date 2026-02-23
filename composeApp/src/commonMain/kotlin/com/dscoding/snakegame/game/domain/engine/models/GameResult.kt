package com.dscoding.snakegame.game.domain.engine.models

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

sealed interface GameResult {

    data class Tick(
        val ateFood: Boolean,
        val snake: List<Pair<Int, Int>>,
        val food: Pair<Int, Int>,
        val movementDirection: MovementDirection
    ) : GameResult

    data class GameEnded(
        val reason: GameEndReason,
    ) : GameResult
}

fun Flow<GameResult>.onTick(action: (GameResult.Tick) -> Unit): Flow<GameResult> =
    onEach { result ->
        if (result is GameResult.Tick) action(result)
    }

fun Flow<GameResult>.onGameEnded(action: (GameResult.GameEnded) -> Unit): Flow<GameResult> =
    onEach { result ->
        if (result is GameResult.GameEnded) action(result)
    }