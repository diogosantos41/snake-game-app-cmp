@file:OptIn(ExperimentalForeignApi::class)

package com.dscoding.snakegame.data.audio

import com.dscoding.snakegame.game.domain.audio.GameAudio
import com.dscoding.snakegame.game.domain.audio.models.SoundEffect
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSBundle

class IosGameAudio : GameAudio {

    private var musicPlayer: AVAudioPlayer? = null

    override fun playSoundEffect(effect: SoundEffect) {
        val fileName = when (effect) {
            SoundEffect.EAT -> "food"
            SoundEffect.GAME_OVER -> "fail"
        }

        val url = NSBundle.mainBundle.URLForResource(fileName, "ogg") ?: return
        AVAudioPlayer(contentsOfURL = url, error = null)?.apply {
            prepareToPlay()
            play()
        }
    }

    override fun startMusic() {
        if (musicPlayer != null) return

        val url = NSBundle.mainBundle.URLForResource("music", "ogg") ?: return
        musicPlayer = AVAudioPlayer(contentsOfURL = url, error = null)?.apply {
            numberOfLoops = -1
            prepareToPlay()
            play()
        }
    }

    override fun stopMusic() {
        musicPlayer?.stop()
        musicPlayer = null
    }
}