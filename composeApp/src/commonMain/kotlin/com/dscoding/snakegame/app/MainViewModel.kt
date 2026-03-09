package com.dscoding.snakegame.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.withIndex

class MainViewModel(
    gamePreferences: GamePreferences
) : ViewModel() {

    val state =
        gamePreferences
            .observeGameColor()
            .distinctUntilChanged()
            .withIndex()
            .map { (index, color) ->
                MainState(
                    primaryColor = color,
                    shouldAnimatePrimaryColor = index > 0,
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainState()
            )
}