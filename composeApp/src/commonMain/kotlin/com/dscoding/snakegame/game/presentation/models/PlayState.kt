package com.dscoding.snakegame.game.presentation.models

sealed interface PlayState {
    data object ReadyToPlay : PlayState
    data object Playing : PlayState
    data object Finished : PlayState
    data class Paused(val pausedState: PausedState) : PlayState
}