package com.dscoding.snakegame.core.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black

val Blue = Color(0xFFA2C0FF)
val Purple = Color(0xFFCAADFF)
val Violet = Color(0xFFFCB6FF)
val Pink = Color(0xFFFFA6AF)

val GameOrange = Color(0xFFD35D17)
val GameYellow = Color(0xFFFAFA05)


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