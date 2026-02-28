package com.dscoding.snakegame.game.domain

import com.dscoding.snakegame.game.domain.audio.GameAudio
import com.dscoding.snakegame.game.domain.audio.models.SoundEffect
import com.dscoding.snakegame.game.domain.engine.GameEngine
import com.dscoding.snakegame.game.domain.engine.models.GameResult
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import com.dscoding.snakegame.game.domain.engine.models.onGameEnded
import com.dscoding.snakegame.game.domain.engine.models.onTick
import com.dscoding.snakegame.game.domain.haptics.GameHaptics
import com.dscoding.snakegame.game.domain.haptics.models.HapticType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class GameCoordinator(
    private val gameEngine: GameEngine,
    private val gameAudio: GameAudio,
    private val gameHaptics: GameHaptics
) {

    var gameEngineJob: Job? = null
        private set

    private var countdownJob: Job? = null

    fun isGameInProgress(): Boolean = gameEngineJob?.isActive == true

    fun startNewGame(
        scope: CoroutineScope,
        boardSize: Int,
        onTick: (GameResult.Tick) -> Unit,
        onGameEnded: () -> Unit
    ) {
        gameAudio.stopAll()
        gameAudio.startMusic()
        gameEngineJob?.cancel()
        gameEngineJob = null
        gameEngineJob = gameEngine
            .runGame(boardSize = boardSize)
            .onTick { tick ->
                if (tick.ateFood) {
                    gameAudio.playSoundEffect(SoundEffect.EAT)
                    gameHaptics.vibrate(HapticType.LIGHT)
                }
                onTick(tick)
            }
            .onGameEnded {
                gameAudio.stopMusic()
                gameAudio.playSoundEffect(SoundEffect.GAME_OVER)
                gameHaptics.vibrate(HapticType.HEAVY)
                gameEngineJob?.cancel()
                gameEngineJob = null
                onGameEnded()
            }
            .launchIn(scope)
    }

    fun pauseGame() {
        if (!isGameInProgress()) return
        countdownJob?.cancel()
        countdownJob = null
        gameEngine.pauseGame()
        gameAudio.stopMusic()
    }

    fun resumeWithCountdown(
        scope: CoroutineScope,
        onCountdown: (Int?) -> Unit,
        onResumed: () -> Unit
    ) {
        countdownJob?.cancel()
        countdownJob = scope.launch {
            for (t in 3 downTo 1) {
                onCountdown(t)
                delay(1_000)
            }
            onCountdown(null)
            gameEngine.resumeGame()
            gameAudio.startMusic()
            onResumed()
        }
    }

    fun requestDirectionChange(direction: MovementDirection) {
        gameEngine.requestDirectionChange(direction)
    }
}