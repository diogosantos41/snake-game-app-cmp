package com.dscoding.snakegame.game.domain.game_engine

import com.dscoding.snakegame.game.domain.game_engine.models.GameEngineResult
import com.dscoding.snakegame.game.domain.game_engine.models.MovementDirection
import kotlinx.coroutines.flow.Flow

interface GameEngine {
    fun runGame(boardSize: Int): Flow<GameEngineResult>

    fun requestDirectionChange(movementDirection: MovementDirection)
    fun pauseGame()
    fun resumeGame()
}