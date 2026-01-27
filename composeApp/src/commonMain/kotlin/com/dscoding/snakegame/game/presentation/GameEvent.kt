package com.dscoding.snakegame.game.presentation

sealed interface GameEvent {
    data class GameOver(val score: Int): GameEvent
}