@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.dscoding.snakegame.game.presentation.utils

import androidx.compose.runtime.*
import platform.Foundation.NSNotificationCenter
import platform.UIKit.*

@Composable
actual fun isLandscapeMobile(): State<Boolean> {
    val state = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        fun update() {
            val orientation = UIDevice.currentDevice.orientation

            val isLandscape = when (orientation) {
                UIDeviceOrientation.UIDeviceOrientationLandscapeLeft,
                UIDeviceOrientation.UIDeviceOrientationLandscapeRight -> true
                else -> false
            }

            val isPhone = UIDevice.currentDevice.userInterfaceIdiom.toInt() == 0

            state.value = isLandscape && isPhone
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