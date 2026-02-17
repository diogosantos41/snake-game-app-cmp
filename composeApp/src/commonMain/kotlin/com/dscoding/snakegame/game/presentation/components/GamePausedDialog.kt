package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dscoding.snakegame.core.presentation.theme.GameOrange
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.game_paused
import snakegame.composeapp.generated.resources.restart
import snakegame.composeapp.generated.resources.resume

@Composable
fun GamePausedDialog(
    currentScore: Int,
    onResumeClick: () -> Unit,
    onRestartClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Black.copy(alpha = 0.7f))
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.game_paused),
                        textAlign = TextAlign.Center,
                        color = White,
                    )
                }

            Text(
                text = "Score: $currentScore",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = White,
            )
            ActionButton(
                text = stringResource(Res.string.resume),
                onClick = onResumeClick,
                modifier = Modifier.padding(horizontal = 60.dp)
            )
            ActionButton(
                text = stringResource(Res.string.restart),
                onClick = onRestartClick,
                modifier = Modifier.padding(horizontal = 60.dp)
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
                onDismiss = {}
            )
        }
    }
}