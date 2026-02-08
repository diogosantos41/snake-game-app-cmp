package com.dscoding.snakegame

import androidx.compose.ui.window.ComposeUIViewController
import com.dscoding.snakegame.di.initKoin

// TODO initKoin should not be called here?
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}