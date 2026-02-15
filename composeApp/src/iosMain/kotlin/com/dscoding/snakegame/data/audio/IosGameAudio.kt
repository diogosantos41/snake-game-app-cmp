@file:OptIn(ExperimentalForeignApi::class)

package com.dscoding.snakegame.data.audio

import com.dscoding.snakegame.game.data.audio.GameAudioDefaults.SFX_VOLUME
import com.dscoding.snakegame.game.data.audio.GameAudioDefaults.MUSIC_VOLUME
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

        val url = NSBundle.mainBundle.URLForResource(
            name = fileName,
            withExtension = "mp3"
        ) ?: return

        AVAudioPlayer(contentsOfURL = url, error = null)?.apply {
            volume = SFX_VOLUME
            prepareToPlay()
            play()
        }
    }

    override fun startMusic() {
        if (musicPlayer != null) return

        val url = NSBundle.mainBundle.URLForResource(
            name = "music",
            withExtension = "mp3"
        ) ?: return

        musicPlayer = AVAudioPlayer(contentsOfURL = url, error = null)?.apply {
            numberOfLoops = -1
            volume = MUSIC_VOLUME
            prepareToPlay()
            play()
        }
    }

    override fun stopMusic() {
        musicPlayer?.stop()
        musicPlayer = null
    }
}