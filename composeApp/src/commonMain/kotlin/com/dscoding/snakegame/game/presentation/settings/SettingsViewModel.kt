package com.dscoding.snakegame.game.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.presentation.settings.models.ColorUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val gamePreferences: GamePreferences,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeGamePreferences()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnFoodColorSelected -> {
                viewModelScope.launch {
                    gamePreferences.setFoodColor(
                        ColorUi.valueOf(action.color.name)
                    )
                }
            }

            is SettingsAction.OnGameColorSelected -> {
                viewModelScope.launch {
                    gamePreferences.setGameColor(
                        ColorUi.valueOf(action.color.name)
                    )
                }
            }

            is SettingsAction.OnToggleControlModeClick -> {
                viewModelScope.launch {
                    gamePreferences.setControlMode(action.controlMode)
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

    private fun observeGamePreferences() {
        combine(
            gamePreferences.observeSoundEnabled(),
            gamePreferences.observeHapticsEnabled(),
            gamePreferences.observeControlMode(),
            gamePreferences.observeGameColor(),
            gamePreferences.observeFoodColor(),
        ) { soundEnabled, hapticsEnabled, controlMode, gameColor, foodColor ->
            _state.update {
                it.copy(
                    isSoundEnabled = soundEnabled,
                    isVibrationEnabled = hapticsEnabled,
                    selectedControlMode = controlMode,
                    selectedGameColor = gameColor,
                    selectedFoodColor = foodColor

                )
            }
        }.launchIn(viewModelScope)
    }

}