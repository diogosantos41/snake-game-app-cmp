package com.dscoding.snakegame.data.haptics

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import com.dscoding.snakegame.game.domain.haptics.models.HapticType

class AndroidGameHaptics(context: Context) : GameHaptics {

    private val vibrator = context.getSystemService(Vibrator::class.java)

    override fun vibrate(type: HapticType) {
        val effect = when (type) {
            HapticType.LIGHT -> VibrationEffect.createOneShot(15, 50)
            HapticType.HEAVY -> VibrationEffect.createOneShot(250, 100)
        }
        vibrator?.vibrate(effect)
    }
}