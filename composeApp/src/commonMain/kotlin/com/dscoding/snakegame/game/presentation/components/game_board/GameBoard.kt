package com.dscoding.snakegame.game.presentation.components.game_board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.util.tileGridBackground
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import com.dscoding.snakegame.game.presentation.GameViewModel.Companion.BOARD_SIZE

@Composable
fun GameBoard(
    food: Pair<Int, Int>?,
    snake: List<Pair<Int, Int>>,
    currentMovementDirection: MovementDirection,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints {
        val tileSize = maxWidth / BOARD_SIZE
        Box(
            modifier = modifier
                .size(maxWidth)
                .border(0.3.dp, White.copy(alpha = 0.3f))
        ) {
            food?.let { food ->
                SnakeFood(
                    offsetX = tileSize * (food.first),
                    offsetY = tileSize * (food.second),
                    size = tileSize
                )
            }
            snake.forEachIndexed { index, body ->
                val darknessStep = 0.005f
                val shade = (1f - index * darknessStep).coerceIn(0.6f, 1f)

                val bodyColor = Color(
                    red = shade,
                    green = shade,
                    blue = shade,
                    alpha = 1f
                )

                if (index == 0) {
                    SnakeHead(
                        offsetX = tileSize * body.first,
                        offsetY = tileSize * body.second,
                        size = tileSize,
                        movementDirection = currentMovementDirection
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .offset(
                                x = tileSize * body.first,
                                y = tileSize * body.second
                            )
                            .size(tileSize)
                            .padding(2.dp)
                            .background(bodyColor)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GameBoardPreview() {
    SnakeGameTheme {
        BoxWithConstraints {
            val tileSize = maxWidth / BOARD_SIZE
            GameBoard(
                food = 2 to 3,
                snake = (1..6).map { it to 1 },
                currentMovementDirection = MovementDirection.LEFT,
                modifier = Modifier.fillMaxSize().tileGridBackground(tileSize = tileSize)
            )
        }
    }
}