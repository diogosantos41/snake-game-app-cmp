package com.dscoding.snakegame.game.domain

import com.dscoding.snakegame.game.domain.models.GameEngineResult
import com.dscoding.snakegame.game.domain.models.MovementInput
import kotlinx.coroutines.flow.Flow

interface GameEngine {
    fun runGame(boardSize: Int): Flow<GameEngineResult>

    fun requestDirectionChange(movementInput: MovementInput)
    fun pauseGame()
    fun resumeGame()
}