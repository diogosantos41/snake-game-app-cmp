@file:OptIn(ExperimentalForeignApi::class)

package com.dscoding.snakegame.data.audio

import com.dscoding.snakegame.core.domain.GamePreferences
import com.dscoding.snakegame.game.data.audio.GameAudioDefaults.MUSIC_VOLUME
import com.dscoding.snakegame.game.data.audio.GameAudioDefaults.SFX_VOLUME
import com.dscoding.snakegame.game.domain.audio.GameAudio
import com.dscoding.snakegame.game.domain.audio.models.SoundEffect
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSBundle

class IosGameAudio(
    gamePreferences: GamePreferences,
    applicationScope: CoroutineScope
) : GameAudio {

    private val isSoundEnabled = gamePreferences
        .observeSoundEnabled()
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly,
            initialValue = true
        )

    private var musicPlayer: AVAudioPlayer? = null

    private val sfxPlayers = mutableMapOf<SoundEffect, AVAudioPlayer>()

    override fun playSoundEffect(effect: SoundEffect) {
        if (!isSoundEnabled.value) return

        val fileName = when (effect) {
            SoundEffect.EAT -> "food"
            SoundEffect.GAME_OVER -> "fail"
        }

        val url = NSBundle.mainBundle.URLForResource(
            name = fileName,
            withExtension = "mp3"
        ) ?: return

        val player = sfxPlayers.getOrPut(effect) {
            AVAudioPlayer(contentsOfURL = url, error = null).apply {
                volume = SFX_VOLUME
                prepareToPlay()
            }
        }

        player.stop()
        player.currentTime = 0.0
        player.play()
    }

    override fun startMusic() {
        if (!isSoundEnabled.value) return
        if (musicPlayer != null) return

        val url = NSBundle.mainBundle.URLForResource(
            name = "music",
            withExtension = "mp3"
        ) ?: return

        musicPlayer = AVAudioPlayer(contentsOfURL = url, error = null).apply {
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

    override fun stopAll() {
        stopMusic()
        sfxPlayers.values.forEach { it.stop() }
    }
}