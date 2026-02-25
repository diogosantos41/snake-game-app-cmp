package com.dscoding.snakegame.game.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.snakegame.core.presentation.components.GameDialogContent
import com.dscoding.snakegame.core.presentation.components.GameDialogHeader
import com.dscoding.snakegame.core.presentation.theme.Dimens.HorizontalSpacingDialogComponent
import com.dscoding.snakegame.core.presentation.theme.Dimens.VerticalSpacingBetweenDialogComponent
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.presentation.settings.components.ColorSettings
import com.dscoding.snakegame.game.presentation.settings.components.LabelSetting
import com.dscoding.snakegame.game.presentation.settings.components.SwitchSetting
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.game_color
import snakegame.composeapp.generated.resources.settings
import snakegame.composeapp.generated.resources.settings_app_version_title

@Composable
fun SettingsRoot(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GameDialogContent(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        SettingsScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    is SettingsAction.OnDismissClick -> onDismiss()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                vertical = VerticalSpacingBetweenDialogComponent,
                horizontal = HorizontalSpacingDialogComponent
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(VerticalSpacingBetweenDialogComponent)
    ) {
        GameDialogHeader(
            title = stringResource(Res.string.settings),
            showClose = true,
            onCloseClick = { onAction(SettingsAction.OnDismissClick) },
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalDivider()
        SwitchSetting(
            title = state.soundSwitchSetting.title.asString(),
            value = state.soundSwitchSetting.valueText.asString(),
            enabled = state.soundSwitchSetting.checked,
            onToggle = { onAction(SettingsAction.OnToggleSoundClick(it)) },
            modifier = Modifier.fillMaxWidth()
        )
        SwitchSetting(
            title = state.vibrationSwitchSetting.title.asString(),
            value = state.vibrationSwitchSetting.valueText.asString(),
            enabled = state.vibrationSwitchSetting.checked,
            onToggle = { onAction(SettingsAction.OnToggleVibrationClick(it)) },
            modifier = Modifier.fillMaxWidth()
        )
        SwitchSetting(
            title = state.controlModeSwitchSetting.title.asString(),
            value = state.controlModeSwitchSetting.valueText.asString(),
            enabled = state.controlModeSwitchSetting.checked,
            onToggle = {
                onAction(
                    SettingsAction.OnToggleControlModeClick(it)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        ColorSettings(
            title = stringResource(Res.string.game_color),
            selectedColor = state.selectedGameColor,
            onColorSelected = { onAction(SettingsAction.OnGameColorSelected(it)) },
            modifier = Modifier.fillMaxWidth()
        )
        LabelSetting(
            title = stringResource(Res.string.settings_app_version_title),
            value = "1.0.0",
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SnakeGameTheme {
        GameDialogContent(onDismiss = {}) {
            SettingsScreen(
                state = SettingsState(),
                onAction = {}
            )
        }
    }
}