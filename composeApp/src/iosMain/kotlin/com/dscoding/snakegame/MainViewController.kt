package com.dscoding.snakegame

import androidx.compose.ui.window.ComposeUIViewController
import com.dscoding.snakegame.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}