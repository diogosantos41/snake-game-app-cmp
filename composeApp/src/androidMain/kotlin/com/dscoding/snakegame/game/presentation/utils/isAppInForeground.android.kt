package com.dscoding.snakegame.game.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
actual fun isAppInForeground(): State<Boolean> {
    val lifecycleOwner = LocalLifecycleOwner.current

    return produceState(initialValue = true, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> value = true
                Lifecycle.Event.ON_PAUSE -> value = false
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        awaitDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}