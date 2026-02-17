package com.dscoding.snakegame.core.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

val GameOrange = Color(0xFFD35D17)
val GameYellow = Color(0xFFFAFA05)

val ContainerBorderWhite = White.copy(alpha = 0.5f)
val ContainerBackgroundBlack = Black.copy(alpha = 0.5f)

val orangeAlphaGradient: Brush
    get() = Brush.verticalGradient(
        listOf(
            GameOrange.copy(alpha = 0.85f),
            GameOrange.copy(alpha = 1f),
        )
    )

val blackAlphaGradient: Brush
    get() = Brush.verticalGradient(
        listOf(
            Black.copy(alpha = 0.4f),
            Black.copy(alpha = 0.6f),
        )
    )