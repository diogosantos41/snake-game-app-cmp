package com.dscoding.snakegame.core.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

val GameOrange = Color(0xFFD35D17)
val GamePink = Color(0xFFE05C9A)
val GamePurple = Color(0xFF7C5AD3)
val GameBlue = Color(0xFF0393EA)
val GameGreen = Color(0xFF8DA25A)

val FoodYellow = Color(0xFFFAFA05)

val ContainerBorderWhite = White.copy(alpha = 0.5f)
val ContainerBackgroundBlack = Black.copy(alpha = 0.5f)
val DialogBackgroundBlack = Black.copy(alpha = 0.8f)

fun alphaVerticalGradient(
    color: Color,
    startAlpha: Float = 0.85f,
    endAlpha: Float = 1f
): Brush {
    return Brush.verticalGradient(
        listOf(
            color.copy(alpha = startAlpha),
            color.copy(alpha = endAlpha),
        )
    )
}

val blackAlphaGradient: Brush
    get() = Brush.verticalGradient(
        listOf(
            Black.copy(alpha = 0.4f),
            Black.copy(alpha = 0.6f),
        )
    )