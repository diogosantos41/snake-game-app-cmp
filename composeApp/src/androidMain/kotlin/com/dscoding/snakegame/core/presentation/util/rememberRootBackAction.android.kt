package com.dscoding.snakegame.core.presentation.util

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


@Composable
actual fun rememberRootBackAction(): () -> Unit {
    val activity = LocalActivity.current
    return remember(activity) {
        { activity?.finish() }
    }
}