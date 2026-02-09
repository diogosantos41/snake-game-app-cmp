package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.GameYellow
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun SnakeFood(offsetX: Dp, offsetY: Dp, size: Dp) {

    val outerRingWidth = 4.dp

    Box(
        modifier = Modifier.offset(
            x = offsetX,
            y = offsetY
        ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .padding(outerRingWidth)
                .background(
                    GameYellow, CircleShape
                )

        )
        Box(
            modifier = Modifier
                .size(size)
                .background(
                    GameYellow.copy(alpha = 0.3f), CircleShape
                )
        )
    }
}

@Preview
@Composable
fun SnakeFoodPreview() {
    SnakeGameTheme {
        SnakeFood(
            offsetX = 0.dp,
            offsetY = 0.dp,
            size = 20.dp
        )
    }
}
