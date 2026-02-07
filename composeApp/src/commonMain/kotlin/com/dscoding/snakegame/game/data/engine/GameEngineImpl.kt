package com.dscoding.snakegame.game.data.engine

import com.dscoding.snakegame.game.domain.GameEngine
import com.dscoding.snakegame.game.domain.models.GameEndReason
import com.dscoding.snakegame.game.domain.models.GameEngineResult
import com.dscoding.snakegame.game.domain.models.MovementInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class GameEngineImpl : GameEngine {

    companion object {
        const val TICK_SPEED = 140L
        const val SNAKE_START_LENGTH = 3
        const val INPUT_BUFFER_SIZE = 3
    }

    private var lastMove: Pair<Int, Int> = MovementInput.RIGHT.delta
    private val pendingNextMoves = ArrayDeque<Pair<Int, Int>>(INPUT_BUFFER_SIZE)

    private var gameBoardSize = 0

    private val isPaused = MutableStateFlow(false)

    override fun runGame(boardSize: Int): Flow<GameEngineResult> = flow {
        gameBoardSize = boardSize
        resetGame()
        var snakeLength = SNAKE_START_LENGTH
        var snake: List<Pair<Int, Int>> = listOf(7 to 7)
        var food: Pair<Int, Int> = spawnFoodAvoidingSnake(snake)

        emit(GameEngineResult.Tick(ateFood = false, food = food, snake = snake))

        while (!isPaused.value) {
            delay(TICK_SPEED)

            val move = pendingNextMoves.removeFirstOrNull() ?: lastMove
            lastMove = move

            val snakeHeadPosition = snake.first()
            val newSnakeHeadPosition =
                ((snakeHeadPosition.first + move.first + gameBoardSize) % gameBoardSize) to
                        ((snakeHeadPosition.second + move.second + gameBoardSize) % gameBoardSize)

            val snakeAteFood = newSnakeHeadPosition == food
            val snakeHitItself = snake.contains(newSnakeHeadPosition)

            if (snakeHitItself) {
                emit(GameEngineResult.GameEnded(reason = GameEndReason.HitSelf))
                return@flow
            }

            if (snakeAteFood) {
                snakeLength++
                food = spawnFoodAvoidingSnake(snake)
            }

            snake = listOf(newSnakeHeadPosition) + snake.take(snakeLength - 1)

            emit(GameEngineResult.Tick(ateFood = snakeAteFood, food = food, snake = snake))
        }
    }

    override fun requestDirectionChange(movementInput: MovementInput) {
        if (pendingNextMoves.size >= INPUT_BUFFER_SIZE) return

        val requestedMove = movementInput.delta
        val intendedMove = currentIntendedDirection()

        if (!isReverse(requestedMove, intendedMove) && requestedMove != intendedMove) {
            pendingNextMoves.addLast(requestedMove)
        }
    }

    override fun pauseGame() {
        pendingNextMoves.clear()
        isPaused.value = true
    }

    override fun resumeGame() {
        isPaused.value = false
    }

    private fun resetGame() {
        pendingNextMoves.clear()
        lastMove = MovementInput.RIGHT.delta
        isPaused.value = false
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
}