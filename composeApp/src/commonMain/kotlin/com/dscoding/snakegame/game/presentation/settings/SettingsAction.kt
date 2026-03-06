package com.dscoding.snakegame.game.presentation.settings

import com.dscoding.snakegame.core.domain.models.ColorUi

sealed interface SettingsAction {
    data class OnToggleSoundClick(val enabled: Boolean) : SettingsAction
    data class OnToggleVibrationClick(val enabled: Boolean) : SettingsAction
    data class OnToggleControlModeClick(val isSwipeSelected: Boolean) : SettingsAction
    data class OnGameColorSelected(val color: ColorUi) : SettingsAction
    data object OnDismissClick : SettingsAction
}