package com.dscoding.snakegame.app

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.snakegame.core.presentation.theme.GameGreen
import com.dscoding.snakegame.core.presentation.theme.GameOrange
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.presentation.GameRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val primary = state.primaryColor?.color ?: GameGreen
    val primaryAnim = remember { Animatable(primary) }

    LaunchedEffect(state.primaryColor) {
        state.primaryColor?.let { colorUi ->
            if (state.shouldAnimatePrimaryColor) {
                primaryAnim.animateTo(colorUi.color, animationSpec = tween(600))
            } else {
                primaryAnim.snapTo(colorUi.color)
            }
        }
    }

    SnakeGameTheme(
        primary = primaryAnim.value,
    ) {
        GameRoot()
    }
}