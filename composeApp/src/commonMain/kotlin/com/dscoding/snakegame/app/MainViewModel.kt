package com.dscoding.snakegame.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    gamePreferences: GamePreferences
) : ViewModel() {

    val state = combine(
        gamePreferences.observeGameColor(),
        gamePreferences.observeFoodColor()
    ) { gameColor, foodColor ->
        MainState(
            primaryColor = gameColor,
            secondaryColor = foodColor
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainState()
    )
}