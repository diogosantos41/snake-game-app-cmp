package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.ContainerBackgroundBlack
import com.dscoding.snakegame.core.presentation.theme.ContainerBorderWhite
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerBorderWidth
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme


@Composable
fun StartCountdown(
    secondsRemaining: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Black.copy(alpha = 0.75f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(size = ContainerRoundedCornerShapeSize),
                color = ContainerBackgroundBlack,
                border = BorderStroke(
                    width = ContainerBorderWidth,
                    color = ContainerBorderWhite
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                ) {
                    Text(
                        text = secondsRemaining,
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StartCountdownPreview() {
    SnakeGameTheme {
        StartCountdown(
            secondsRemaining = "2",
            modifier = Modifier.fillMaxSize()
        )
    }
}