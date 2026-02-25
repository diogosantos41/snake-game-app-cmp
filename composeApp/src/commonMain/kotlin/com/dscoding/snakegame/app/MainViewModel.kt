package com.dscoding.snakegame.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    gamePreferences: GamePreferences
) : ViewModel() {

    val state =
        gamePreferences
            .observeGameColor()
            .distinctUntilChanged()
            .map { gameColor ->
                MainState(
                    primaryColor = gameColor
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainState()
            )
}