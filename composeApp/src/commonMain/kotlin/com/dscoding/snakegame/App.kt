package com.dscoding.snakegame

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.presentation.GameRoot

@Composable
@Preview
fun App() {
    SnakeGameTheme {
        GameRoot()
    }
}