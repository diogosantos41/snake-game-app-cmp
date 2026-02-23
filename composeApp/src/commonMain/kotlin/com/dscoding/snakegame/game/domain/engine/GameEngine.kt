package com.dscoding.snakegame.game.domain.engine

import com.dscoding.snakegame.game.domain.engine.models.GameResult
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import kotlinx.coroutines.flow.Flow

interface GameEngine {
    fun runGame(boardSize: Int): Flow<GameResult>
    fun requestDirectionChange(movementDirection: MovementDirection)
    fun resumeGame()
    fun pauseGame()


}