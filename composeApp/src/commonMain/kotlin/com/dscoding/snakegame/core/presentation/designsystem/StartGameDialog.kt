package com.dscoding.snakegame.core.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.Violet
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.start

@Composable
fun StartGameDialog(
    title: String? = null,
    onStartGameClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(White)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            title?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Violet)
                        .padding(15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        color = White,
                    )
                }
            }
            ActionButton(
                text = stringResource(Res.string.start),
                onClick = onStartGameClick,
                modifier = Modifier.padding(25.dp)
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
                title = "Snake Game",
                onStartGameClick = {},
                onDismiss = {}
            )
        }
    }
}