package com.dscoding.snakegame.game.presentation.components.game_board

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.GameYellow
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun SnakeFood(offsetX: Dp, offsetY: Dp, size: Dp) {

    val outerRingWidth = 4.dp

    val infinite = rememberInfiniteTransition(label = "foodPulse")
    val pulse by infinite.animateFloat(
        initialValue = 0.90f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .scale(pulse),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .scale(pulse)
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
private fun SnakeFoodPreview() {
    SnakeGameTheme {
        SnakeFood(
            offsetX = 0.dp,
            offsetY = 0.dp,
            size = 20.dp
        )
    }
}
