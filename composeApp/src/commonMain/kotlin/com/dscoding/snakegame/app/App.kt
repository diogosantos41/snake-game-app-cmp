package com.dscoding.snakegame.app

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.presentation.GameRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val primary = state.primaryColor?.color ?: Transparent
    val primaryAnim = remember { Animatable(primary) }

    LaunchedEffect(primary) {
        primaryAnim.animateTo(
            targetValue = primary,
            animationSpec = tween(300)
        )
    }

    SnakeGameTheme(
        primary = primaryAnim.value,
    ) {
        GameRoot()
    }
}