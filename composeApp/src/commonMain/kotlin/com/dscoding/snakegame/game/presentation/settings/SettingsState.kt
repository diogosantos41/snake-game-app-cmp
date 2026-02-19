package com.dscoding.snakegame.game.presentation.settings

import com.dscoding.snakegame.game.presentation.models.ControlMode

data class SettingsState(
    val isSoundEnabled: Boolean = true,
    val isVibrationEnabled: Boolean = true,
    val selectedControlMode: ControlMode = ControlMode.SWIPE,
    val selectedGameColor: Int = 0,
    val selectedFoodColor: Int = 0
)