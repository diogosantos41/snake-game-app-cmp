package com.dscoding.snakegame.game.presentation.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun isOrientationLandscape(): State<Boolean> {
    val config = LocalConfiguration.current
    return remember(config) {
        derivedStateOf { config.orientation == Configuration.ORIENTATION_LANDSCAPE }
    }
}