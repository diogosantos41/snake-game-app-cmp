package com.dscoding.snakegame.game.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dscoding.snakegame.core.presentation.components.GameDialogContent
import com.dscoding.snakegame.core.presentation.theme.GameBlue
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GameDialogContent(onDismiss = {
        viewModel.onAction(SettingsAction.OnDismissDialog)
    }) {
        SettingsScreen(
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    Box(Modifier.size(150.dp).background(GameBlue)) {}
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SnakeGameTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}