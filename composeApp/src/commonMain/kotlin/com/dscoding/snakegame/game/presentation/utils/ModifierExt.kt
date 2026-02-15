package com.dscoding.snakegame.game.presentation.utils

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.game.domain.game_engine.models.MovementDirection
import kotlin.math.abs

fun Modifier.snakeSwipeControls(
    enabled: Boolean,
    threshold: Dp = 12.dp,
    onDirection: (MovementDirection) -> Unit
): Modifier = pointerInput(enabled, threshold) {
    if (!enabled) return@pointerInput

    val thresholdPx = threshold.toPx()
    var directionLocked = false

    var totalDx = 0f
    var totalDy = 0f

    fun reset() {
        directionLocked = false
        totalDx = 0f
        totalDy = 0f
    }

    detectDragGestures(
        onDragStart = { reset() },
        onDragEnd = { reset() },
        onDragCancel = { reset() },
        onDrag = { change, dragAmount ->
            change.consume()
            if (directionLocked) return@detectDragGestures

            totalDx += dragAmount.x
            totalDy += dragAmount.y

            if (abs(totalDx) < thresholdPx && abs(totalDy) < thresholdPx) {
                return@detectDragGestures
            }

            val dir = if (abs(totalDx) > abs(totalDy)) {
                if (totalDx > 0) MovementDirection.RIGHT else MovementDirection.LEFT
            } else {
                if (totalDy > 0) MovementDirection.DOWN else MovementDirection.UP
            }

            directionLocked = true
            onDirection(dir)
        }
    )
}