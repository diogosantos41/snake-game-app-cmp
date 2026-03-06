package com.dscoding.snakegame.core.domain

import com.dscoding.snakegame.core.domain.models.ControlMode
import com.dscoding.snakegame.core.domain.models.ColorUi
import kotlinx.coroutines.flow.Flow

interface GamePreferences {
    suspend fun setHighscore(value: Int)
    fun observeHighscore(): Flow<Int>
    suspend fun setSoundEnabled(isEnabled: Boolean)
    fun observeSoundEnabled(): Flow<Boolean>
    suspend fun setHapticsEnabled(isEnabled: Boolean)
    fun observeHapticsEnabled(): Flow<Boolean>
    suspend fun setControlMode(controlMode: ControlMode)
    fun observeControlMode(): Flow<ControlMode>
    suspend fun setGameColor(color: ColorUi)
    fun observeGameColor(): Flow<ColorUi>
}