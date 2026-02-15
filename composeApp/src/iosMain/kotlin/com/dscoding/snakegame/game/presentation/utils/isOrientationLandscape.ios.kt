@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.dscoding.snakegame.game.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import platform.Foundation.NSNotificationCenter
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation
import platform.UIKit.UIDeviceOrientationDidChangeNotification

@Composable
actual fun isOrientationLandscape(): State<Boolean> {
    val state = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        fun update() {
            val orientation = UIDevice.currentDevice.orientation
            state.value = when (orientation) {
                UIDeviceOrientation.UIDeviceOrientationLandscapeLeft,
                UIDeviceOrientation.UIDeviceOrientationLandscapeRight -> true
                else -> false
            }
        }

        UIDevice.currentDevice.beginGeneratingDeviceOrientationNotifications()
        update()

        val center = NSNotificationCenter.defaultCenter
        val token = center.addObserverForName(
            name = UIDeviceOrientationDidChangeNotification,
            `object` = null,
            queue = null,
            usingBlock = { _ -> update() }
        )

        onDispose {
            center.removeObserver(token)
            UIDevice.currentDevice.endGeneratingDeviceOrientationNotifications()
        }
    }

    return state
}