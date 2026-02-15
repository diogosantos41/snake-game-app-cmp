package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme


@Composable
fun StartCountdown(
    secondsRemaining: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Black.copy(alpha = 0.5f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
            ) {
                Text(
                    text = secondsRemaining,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = Bold
                    )
                )
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