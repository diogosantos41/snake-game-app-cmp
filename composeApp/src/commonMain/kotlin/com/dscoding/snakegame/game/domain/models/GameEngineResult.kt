package com.dscoding.snakegame.game.domain.models

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

sealed interface GameEngineResult {

    data class Tick(
        val ateFood: Boolean,
        val snake: List<Pair<Int, Int>>,
        val food: Pair<Int, Int>,
    ) : GameEngineResult

    data class GameEnded(
        val reason: GameEndReason,
    ) : GameEngineResult
}

fun Flow<GameEngineResult>.onTick(action: (GameEngineResult.Tick) -> Unit): Flow<GameEngineResult> =
    onEach { result ->
        if (result is GameEngineResult.Tick) action(result)
    }

fun Flow<GameEngineResult>.onGameEnded(action: (GameEngineResult.GameEnded) -> Unit): Flow<GameEngineResult> =
    onEach { result ->
        if (result is GameEngineResult.GameEnded) action(result)
    }