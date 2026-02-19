package com.dscoding.snakegame.core.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White




val GameOrange  = Color(0xFFD35D17)
val GameRed     = Color(0xFFE95F52)
val GameCoral   = Color(0xFFFF7A6A)
val GamePink    = Color(0xFFE05C9A)
val GamePurple  = Color(0xFF7C5AD3)
val GameIndigo  = Color(0xFF4F6EDB)
val GameBlue    = Color(0xFF0393EA)
val GameCyan    = Color(0xFF00B8B8)
val GameMint    = Color(0xFF4DBA9B)
val GameGreen   = Color(0xFF8DA25A)
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