package com.dscoding.snakegame.game.presentation.settings

import com.dscoding.snakegame.game.presentation.settings.models.ColorUi
import com.dscoding.snakegame.game.presentation.settings.models.SwitchSettingUi

data class SettingsState(
    val soundSwitchSetting: SwitchSettingUi = SwitchSettingUi(),
    val vibrationSwitchSetting: SwitchSettingUi = SwitchSettingUi(),
    val controlModeSwitchSetting: SwitchSettingUi = SwitchSettingUi(),
    val selectedGameColor: ColorUi = ColorUi.BURNT_ORANGE,
    val selectedFoodColor: ColorUi = ColorUi.BRIGHT_YELLOW
)