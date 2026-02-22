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
import com.dscoding.snakegame.game.presentation.settings.components.SettingsField
import com.dscoding.snakegame.game.presentation.settings.components.SwitchField
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.control_mode
import snakegame.composeapp.generated.resources.enabled
import snakegame.composeapp.generated.resources.settings
import snakegame.composeapp.generated.resources.settings_app_version_title
import snakegame.composeapp.generated.resources.sound
import snakegame.composeapp.generated.resources.swipe
import snakegame.composeapp.generated.resources.vibration

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
        SwitchField(
            title = stringResource(Res.string.sound),
            value = stringResource(Res.string.enabled),
            enabled = true,
            onToggle = {},
            modifier = Modifier.fillMaxWidth()
        )
        SwitchField(
            title = stringResource(Res.string.vibration),
            value = stringResource(Res.string.enabled),
            enabled = false,
            onToggle = {},
            modifier = Modifier.fillMaxWidth()
        )
        SwitchField(
            title = stringResource(Res.string.control_mode),
            value = stringResource(Res.string.swipe),
            enabled = true,
            onToggle = {},
            modifier = Modifier.fillMaxWidth()
        )
        SettingsField(
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