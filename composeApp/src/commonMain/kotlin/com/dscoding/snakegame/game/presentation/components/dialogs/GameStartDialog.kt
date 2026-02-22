package com.dscoding.snakegame.game.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.components.GameDialogContent
import com.dscoding.snakegame.core.presentation.components.GameDialogHeader
import com.dscoding.snakegame.core.presentation.theme.Dimens.HorizontalSpacingDialogComponent
import com.dscoding.snakegame.core.presentation.theme.Dimens.VerticalSpacingBetweenDialogComponent
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.presentation.components.ActionButton
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.settings
import snakegame.composeapp.generated.resources.snake_game
import snakegame.composeapp.generated.resources.start

@Composable
fun StartGameDialog(
    onStartGameClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GameDialogContent(
        onDismiss = onDismiss,
        dismissOnBackPress = false,
        modifier = modifier
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
                title = stringResource(Res.string.snake_game),
                showClose = false,
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()
            ActionButton(
                text = stringResource(Res.string.start),
                onClick = onStartGameClick,
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                text = stringResource(Res.string.settings),
                onClick = onSettingsClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun StartGameDialogPreview() {
    SnakeGameTheme {
        Box(modifier = Modifier.padding(all = 150.dp)) {
            StartGameDialog(
                onStartGameClick = {},
                onSettingsClick = {},
                onDismiss = {}
            )
        }
    }
}