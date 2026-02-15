package com.dscoding.snakegame.core.presentation.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.tileGridBackground(
    tileSize: Dp,
    offsetY: Dp = 0.dp,
    lineColor: Color = Color.White.copy(alpha = 0.5f),
    strokeWidth: Float = 0.5f
) = drawBehind {
    val step = tileSize.toPx()
    if (step <= 0f) return@drawBehind

    val offsetYPx = offsetY.toPx() % step

    var x = 0f
    while (x <= size.width) {
        drawLine(
            color = lineColor,
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = strokeWidth
        )
        x += step
    }

    var y = -offsetYPx
    while (y <= size.height) {
        drawLine(
            color = lineColor,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = strokeWidth
        )
        y += step
    }
}