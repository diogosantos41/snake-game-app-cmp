package com.dscoding.snakegame.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SnakeGameTheme(
    primary: Color = GameOrange,
    content: @Composable () -> Unit
) {
    val baseScheme = lightColorScheme()

    MaterialTheme(
        colorScheme = baseScheme.copy(
            primary = primary,
        ),
        typography = Typography,
        content = content
    )
}