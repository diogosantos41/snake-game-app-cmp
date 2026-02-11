package com.dscoding.snakegame.game.presentation.components.game_board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.domain.models.MovementDirection

@Composable
fun SnakeHead(
    offsetX: Dp,
    offsetY: Dp,
    size: Dp,
    movementDirection: MovementDirection = MovementDirection.RIGHT
) {
    val eyeSize = size * 0.18f
    val eyeInset = size * 0.12f

    val tongueWidth = size * 0.12f
    val tongueLength = size * 0.24f
    val tongueInset = size * 0.18f

    val rotation = when (movementDirection) {
        MovementDirection.UP -> 0f
        MovementDirection.RIGHT -> 90f
        MovementDirection.DOWN -> 180f
        MovementDirection.LEFT -> 270f
    }

    Box(
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .size(size)
            .padding(2.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationZ = rotation
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                }
        ) {
            Box(
                modifier = Modifier
                    .offset(x = eyeInset, y = eyeInset)
                    .size(eyeSize)
                    .clip(shape = CircleShape)
                    .background(Color.Black)

            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = -eyeInset, y = eyeInset)
                    .size(eyeSize)
                    .clip(shape = CircleShape)
                    .background(Color.Black)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = -tongueInset)
                    .size(width = tongueWidth, height = tongueLength)
                    .background(Color.Red)
            )
        }
    }
}

@Preview
@Composable
private fun SnakeHeadPreview() {
    SnakeGameTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            SnakeHead(
                offsetY = 0.dp,
                offsetX = 0.dp,
                size = 30.dp,
            )
        }
    }
}