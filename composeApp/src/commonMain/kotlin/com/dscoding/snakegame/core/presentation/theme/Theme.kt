package com.dscoding.snakegame.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun SnakeGameTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography,
        content = content
    )
}