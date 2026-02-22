package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.components.GameDialogContent
import com.dscoding.snakegame.core.presentation.components.GameDialogHeader
import com.dscoding.snakegame.core.presentation.theme.Dimens.HorizontalSpacingDialogComponent
import com.dscoding.snakegame.core.presentation.theme.Dimens.VerticalSpacingBetweenDialogComponent
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.game_paused
import snakegame.composeapp.generated.resources.restart
import snakegame.composeapp.generated.resources.resume
import snakegame.composeapp.generated.resources.score
import snakegame.composeapp.generated.resources.settings

@Composable
fun GamePausedDialog(
    currentScore: Int,
    onResumeClick: () -> Unit,
    onRestartClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GameDialogContent(
        onDismiss = onDismiss,
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
                title = stringResource(Res.string.game_paused),
                showClose = true,
                onCloseClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                    ) {
                        append(stringResource(Res.string.score) + ": ")
                    }
                    withStyle(
                        style = MaterialTheme.typography.labelMedium.toSpanStyle()
                    ) {
                        append("$currentScore")
                    }
                },
                textAlign = TextAlign.Center
            )
            ActionButton(
                text = stringResource(Res.string.resume),
                onClick = onResumeClick,
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                text = stringResource(Res.string.restart),
                onClick = onRestartClick,
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
private fun GamePausedDialogPreview() {
    SnakeGameTheme {
        Box(modifier = Modifier.padding(all = 150.dp)) {
            GamePausedDialog(
                currentScore = 12,
                onResumeClick = {},
                onRestartClick = {},
                onSettingsClick = {},
                onDismiss = {}
            )
        }
    }
}