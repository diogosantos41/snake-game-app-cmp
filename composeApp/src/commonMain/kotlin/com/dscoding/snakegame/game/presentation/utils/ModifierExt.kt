package com.dscoding.snakegame.game.presentation.utils

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.game.domain.models.MovementDirection
import kotlin.math.abs

fun Modifier.snakeSwipeControls(
    enabled: Boolean = true,
    threshold: Dp = 24.dp,
    onDirection: (MovementDirection) -> Unit
): Modifier = pointerInput(enabled, threshold) {
    if (!enabled) return@pointerInput

    val thresholdPx = threshold.toPx()
    var directionLocked = false

    detectDragGestures(
        onDragStart = {
            directionLocked = false
        },
        onDragCancel = {
            directionLocked = false
        },
        onDragEnd = {
            directionLocked = false
        },
        onDrag = { change, dragAmount ->
            change.consume()

            if (directionLocked) return@detectDragGestures

            val dx = dragAmount.x
            val dy = dragAmount.y

            if (abs(dx) < thresholdPx && abs(dy) < thresholdPx) return@detectDragGestures

            val dir = if (abs(dx) > abs(dy)) {
                if (dx > 0) MovementDirection.RIGHT else MovementDirection.LEFT
            } else {
                if (dy > 0) MovementDirection.DOWN else MovementDirection.UP
            }

            directionLocked = true
            onDirection(dir)
        }
    )
}