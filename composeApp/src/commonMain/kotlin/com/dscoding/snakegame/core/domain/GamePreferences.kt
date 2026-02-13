package com.dscoding.snakegame.core.domain

import kotlinx.coroutines.flow.Flow

interface GamePreferences {
    fun observeHighscore(): Flow<Int>
    suspend fun setHighscore(value: Int)
}