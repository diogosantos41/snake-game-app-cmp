@file:OptIn(ExperimentalForeignApi::class)

package com.dscoding.snakegame.game.presentation.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.CoreGraphics.CGRectMake
import platform.UIKit.popoverPresentationController


actual class PlatformShareSheet {
    actual fun shareText(text: String) {
        val itemsToShare = listOf(text)

        val activityViewController = UIActivityViewController(
            activityItems = itemsToShare,
            applicationActivities = null
        )

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            ?: throw IllegalStateException("No root view controller found.")

        // iPad fix: UIActivityViewController is a popover there and needs an anchor
        activityViewController.popoverPresentationController?.let { popover ->
            val view = rootViewController.view
            popover.sourceView = view
            popover.sourceRect = CGRectMake(
                x = view.bounds.useContents { size.width / 2.0 },
                y = view.bounds.useContents { size.height / 2.0 },
                width = 1.0,
                height = 1.0
            )
            popover.permittedArrowDirections = 0UL
        }

        rootViewController.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null
        )
    }
}