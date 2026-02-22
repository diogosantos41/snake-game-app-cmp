package com.dscoding.snakegame.game.presentation.settings

import com.dscoding.snakegame.game.presentation.models.ControlMode
import com.dscoding.snakegame.game.presentation.settings.models.ColorUi

data class SettingsState(
    val isSoundEnabled: Boolean = true,
    val isVibrationEnabled: Boolean = true,
    val selectedControlMode: ControlMode = ControlMode.SWIPE,
    val selectedGameColor: ColorUi = ColorUi.BURNT_ORANGE,
    val selectedFoodColor: ColorUi = ColorUi.BRIGHT_YELLOW
)