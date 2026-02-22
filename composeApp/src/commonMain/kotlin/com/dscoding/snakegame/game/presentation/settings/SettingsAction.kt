package com.dscoding.snakegame.game.presentation.settings

import com.dscoding.snakegame.game.presentation.models.ControlMode

sealed interface SettingsAction {
    data class OnToggleSoundClick(val enabled: Boolean) : SettingsAction
    data class OnToggleVibrationClick(val enabled: Boolean) : SettingsAction
    data class OnToggleControlModeClick(val controlMode: ControlMode) : SettingsAction
    data class OnGameColorSelected(val color: Int) : SettingsAction
    data class OnFoodColorSelected(val color: Int) : SettingsAction
    data object OnRateAppClick : SettingsAction
    data object OnShareAppClick : SettingsAction
    data object OnGithubClick : SettingsAction
    data object OnPrivacyPolicyClick : SettingsAction
    data object OnDismissClick : SettingsAction
}