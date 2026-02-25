package com.dscoding.snakegame.game.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.core.presentation.util.UiText
import com.dscoding.snakegame.game.presentation.models.ControlMode
import com.dscoding.snakegame.game.presentation.settings.models.ColorUi
import com.dscoding.snakegame.game.presentation.settings.models.SwitchSettingUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.control_mode
import snakegame.composeapp.generated.resources.direction_pad
import snakegame.composeapp.generated.resources.disabled
import snakegame.composeapp.generated.resources.enabled
import snakegame.composeapp.generated.resources.sound
import snakegame.composeapp.generated.resources.swipe
import snakegame.composeapp.generated.resources.vibration

class SettingsViewModel(
    private val gamePreferences: GamePreferences,
) : ViewModel() {

    val state = combine(
        gamePreferences.observeSoundEnabled(),
        gamePreferences.observeHapticsEnabled(),
        gamePreferences.observeControlMode(),
        gamePreferences.observeGameColor(),
    ) { soundEnabled, hapticsEnabled, controlMode, gameColor ->
        SettingsState(
            soundSwitchSetting = SwitchSettingUi(
                title = UiText.Resource(Res.string.sound),
                checked = soundEnabled,
                checkedText = UiText.Resource(Res.string.enabled),
                uncheckedText = UiText.Resource(Res.string.disabled),
            ),
            vibrationSwitchSetting = SwitchSettingUi(
                title = UiText.Resource(Res.string.vibration),
                checked = hapticsEnabled,
                checkedText = UiText.Resource(Res.string.enabled),
                uncheckedText = UiText.Resource(Res.string.disabled),
            ),
            controlModeSwitchSetting = SwitchSettingUi(
                title = UiText.Resource(Res.string.control_mode),
                checked = (controlMode == ControlMode.SWIPE),
                checkedText = UiText.Resource(Res.string.swipe),
                uncheckedText = UiText.Resource(Res.string.direction_pad),
            ),
            selectedGameColor = gameColor,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = SettingsState()
    )

    fun onAction(action: SettingsAction) {
        when (action) {

            is SettingsAction.OnGameColorSelected -> {
                viewModelScope.launch {
                    gamePreferences.setGameColor(
                        ColorUi.valueOf(action.color.name)
                    )
                }
            }

            is SettingsAction.OnToggleControlModeClick -> {
                viewModelScope.launch {
                    val mode = if (action.isSwipeSelected) ControlMode.SWIPE else ControlMode.BUTTONS
                    gamePreferences.setControlMode(mode)
                }
            }

            is SettingsAction.OnToggleSoundClick -> {
                viewModelScope.launch {
                    gamePreferences.setSoundEnabled(action.enabled)
                }
            }

            is SettingsAction.OnToggleVibrationClick -> {
                viewModelScope.launch {
                    gamePreferences.setHapticsEnabled(action.enabled)
                }
            }

            SettingsAction.OnGithubClick -> TODO()
            SettingsAction.OnPrivacyPolicyClick -> TODO()
            SettingsAction.OnRateAppClick -> TODO()
            SettingsAction.OnShareAppClick -> TODO()

            else -> Unit
        }
    }
}