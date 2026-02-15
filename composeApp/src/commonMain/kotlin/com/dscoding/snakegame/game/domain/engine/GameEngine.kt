package com.dscoding.snakegame.game.domain.engine

import com.dscoding.snakegame.game.domain.engine.models.GameEngineResult
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import kotlinx.coroutines.flow.Flow

interface GameEngine {
    fun runGame(boardSize: Int): Flow<GameEngineResult>

    fun requestDirectionChange(movementDirection: MovementDirection)
    fun pauseGame()
    fun resumeGame()
}