package com.dscoding.snakegame.game.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UIApplicationDidBecomeActiveNotification
import platform.UIKit.UIApplicationDidEnterBackgroundNotification
import platform.UIKit.UIApplicationWillResignActiveNotification

@Composable
actual fun isAppInForeground(): State<Boolean> {
    val state = remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        val center = NSNotificationCenter.defaultCenter

        val didBecomeActive = center.addObserverForName(
            name = UIApplicationDidBecomeActiveNotification,
            `object` = null,
            queue = null
        ) { _ ->
            state.value = true
        }

        val willResignActive = center.addObserverForName(
            name = UIApplicationWillResignActiveNotification,
            `object` = null,
            queue = null
        ) { _ ->
            state.value = false
        }

        val didEnterBackground = center.addObserverForName(
            name = UIApplicationDidEnterBackgroundNotification,
            `object` = null,
            queue = null
        ) { _ ->
            state.value = false
        }

        onDispose {
            center.removeObserver(didBecomeActive)
            center.removeObserver(willResignActive)
            center.removeObserver(didEnterBackground)
        }
    }

    return state
}