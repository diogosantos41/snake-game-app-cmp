package com.dscoding.snakegame.game.domain.haptics

import com.dscoding.snakegame.game.domain.haptics.models.HapticType

interface GameHaptics {
    fun vibrate(type: HapticType)
}