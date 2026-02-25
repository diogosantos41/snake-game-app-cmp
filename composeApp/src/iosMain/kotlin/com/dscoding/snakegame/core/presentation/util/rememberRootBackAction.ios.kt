package com.dscoding.snakegame.core.presentation.util

import androidx.compose.runtime.Composable

@Composable
actual fun rememberRootBackAction(): () -> Unit = {
    // No system back on iOS root
}



