package com.dscoding.snakegame.data.haptics

import android.content.Context
import android.os.Vibrator
import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import com.dscoding.snakegame.game.domain.haptics.models.HapticType

class AndroidGameHaptics(context: Context) : GameHaptics {

    private val vibrator = context.getSystemService(Vibrator::class.java)

    override fun vibrate(type: HapticType) {
        val effect = when (type) {
            HapticType.LIGHT -> android.os.VibrationEffect.createOneShot(15, 50)
            HapticType.HEAVY -> android.os.VibrationEffect.createOneShot(60, 200)
        }
        vibrator?.vibrate(effect)
    }
}