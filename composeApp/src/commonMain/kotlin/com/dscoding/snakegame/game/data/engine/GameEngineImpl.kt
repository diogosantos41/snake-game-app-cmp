package com.dscoding.snakegame.game.data.engine

import com.dscoding.snakegame.game.data.engine.mappers.toMovementDirection
import com.dscoding.snakegame.game.domain.engine.GameEngine
import com.dscoding.snakegame.game.domain.engine.models.GameEndReason
import com.dscoding.snakegame.game.domain.engine.models.GameResult
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.random.Random

class GameEngineImpl : GameEngine {

    companion object {
        const val TICK_SPEED = 140L
        const val SNAKE_START_LENGTH = 3
        const val INPUT_BUFFER_SIZE = 3
    }

    private val defaultMovementDirection = MovementDirection.RIGHT
    private var lastMove: Pair<Int, Int> = defaultMovementDirection.delta
    private val pendingNextMoves = ArrayDeque<Pair<Int, Int>>(INPUT_BUFFER_SIZE)

    private var gameBoardSize = 0

    private val isPaused = MutableStateFlow(false)

    override fun runGame(boardSize: Int): Flow<GameResult> = flow {
        gameBoardSize = boardSize
        resetGame()
        var snakeLength = SNAKE_START_LENGTH
        var snake: List<Pair<Int, Int>> = listOf(7 to 7)
        var food: Pair<Int, Int> = spawnFoodAvoidingSnake(snake)

        emit(
            GameResult.Tick(
                ateFood = false,
                food = food,
                snake = snake,
                movementDirection = defaultMovementDirection
            )
        )

        while (currentCoroutineContext().isActive) {
            if (isPaused.value) waitUntilResumed()

            delay(TICK_SPEED)

            if (isPaused.value) continue

            val move = pendingNextMoves.removeFirstOrNull() ?: lastMove
            lastMove = move

            val snakeHeadPosition = snake.first()
            val newSnakeHeadPosition =
                ((snakeHeadPosition.first + move.first + gameBoardSize) % gameBoardSize) to
                        ((snakeHeadPosition.second + move.second + gameBoardSize) % gameBoardSize)

            val snakeAteFood = newSnakeHeadPosition == food

            val bodyToCheck = if (snakeAteFood) snake else snake.dropLast(1)

            val snakeHitItself = newSnakeHeadPosition in bodyToCheck
            if (snakeHitItself) {
                emit(GameResult.GameEnded(reason = GameEndReason.HitSelf))
                return@flow
            }

            if (snakeAteFood) {
                snakeLength++
            }

            snake = listOf(newSnakeHeadPosition) + snake.take(snakeLength - 1)

            if (snakeAteFood) {
                if (checkVictory(snake)) {
                    emit(GameResult.GameEnded(reason = GameEndReason.Victory))
                    return@flow
                }
                food = spawnFoodAvoidingSnake(snake)
            }

            emit(
                GameResult.Tick(
                    ateFood = snakeAteFood,
                    food = food,
                    snake = snake,
                    movementDirection = lastMove.toMovementDirection()
                )
            )
        }
    }

    override fun requestDirectionChange(movementDirection: MovementDirection) {
        if (pendingNextMoves.size >= INPUT_BUFFER_SIZE) return

        val requestedMove = movementDirection.delta
        val intendedMove = currentIntendedDirection()

        if (!isReverse(requestedMove, intendedMove) && requestedMove != intendedMove) {
            pendingNextMoves.addLast(requestedMove)
        }
    }

    override fun resumeGame() {
        isPaused.value = false
    }

    override fun pauseGame() {
        isPaused.value = true
        pendingNextMoves.clear()
    }

    private fun resetGame() {
        isPaused.value = false
        pendingNextMoves.clear()
        lastMove = MovementDirection.RIGHT.delta

    }

    private fun isReverse(a: Pair<Int, Int>, b: Pair<Int, Int>): Boolean {
        return a.first == -b.first && a.second == -b.second
    }

    private fun currentIntendedDirection(): Pair<Int, Int> {
        return pendingNextMoves.lastOrNull() ?: lastMove
    }

    private fun spawnFoodAvoidingSnake(snake: List<Pair<Int, Int>>): Pair<Int, Int> {
        while (true) {
            val spawnLocation = Random.nextInt(gameBoardSize) to Random.nextInt(gameBoardSize)
            if (spawnLocation !in snake) return spawnLocation
        }
    }

    private suspend fun waitUntilResumed() {
        isPaused.filter { paused -> !paused }.first()
    }

    private fun checkVictory(snake: List<Pair<Int, Int>>): Boolean {
        val boardArea = gameBoardSize * gameBoardSize
        return snake.size >= boardArea
    }
}