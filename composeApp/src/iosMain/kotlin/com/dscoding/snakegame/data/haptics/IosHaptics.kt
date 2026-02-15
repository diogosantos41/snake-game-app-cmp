package com.dscoding.snakegame.data.haptics

import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import com.dscoding.snakegame.game.domain.haptics.models.HapticType
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle.UIImpactFeedbackStyleLight
import platform.UIKit.UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy

class IosGameHaptics : GameHaptics {
    override fun vibrate(type: HapticType) {
        val style = when (type) {
            HapticType.LIGHT -> UIImpactFeedbackStyleLight
            HapticType.HEAVY -> UIImpactFeedbackStyleHeavy
        }
        UIImpactFeedbackGenerator(style).impactOccurred()
    }
}