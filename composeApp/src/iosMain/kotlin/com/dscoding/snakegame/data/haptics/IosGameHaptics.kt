package com.dscoding.snakegame.data.haptics

import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import com.dscoding.snakegame.game.domain.haptics.models.HapticType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy
import platform.UIKit.UIImpactFeedbackStyle.UIImpactFeedbackStyleLight

class IosGameHaptics(
    gamePreferences: GamePreferences,
    applicationScope: CoroutineScope
) : GameHaptics {

    private val lightGen = UIImpactFeedbackGenerator(UIImpactFeedbackStyleLight)
    private val heavyGen = UIImpactFeedbackGenerator(UIImpactFeedbackStyleHeavy)

    private val isHapticsEnabled = gamePreferences
        .observeHapticsEnabled()
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly,
            initialValue = true
        )

    override fun vibrate(type: HapticType) {
        if (!isHapticsEnabled.value) return

        val impactGen = when (type) {
            HapticType.LIGHT -> lightGen
            HapticType.HEAVY -> heavyGen
        }

        impactGen.prepare()
        impactGen.impactOccurred()
    }
}