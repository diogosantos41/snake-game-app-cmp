package com.dscoding.snakegame.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.presentation.GameViewModel.Companion.BOARD_SIZE
import com.dscoding.snakegame.game.presentation.models.Direction

@Composable
fun GameRoot(
    viewModel: GameViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GameScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun GameScreen(
    state: GameState,
    onAction: (GameAction) -> Unit,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Board(state)
        val buttonSize = Modifier.size(64.dp)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Button(
                onClick = { onAction(GameAction.OnDirectionChanged(Direction.UP)) },
                modifier = buttonSize
            ) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }
            Row {
                Button(
                    onClick = { onAction(GameAction.OnDirectionChanged(Direction.LEFT)) },
                    modifier = buttonSize
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, null)
                }
                Spacer(modifier = buttonSize)
                Button(
                    onClick = { onAction(GameAction.OnDirectionChanged(Direction.RIGHT)) },
                    modifier = buttonSize
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, null)
                }
            }
            Button(
                onClick = { onAction(GameAction.OnDirectionChanged(Direction.DOWN)) },
                modifier = buttonSize
            ) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
        }
    }
}

@Composable
fun Board(state: GameState) {
    BoxWithConstraints(Modifier.padding(16.dp)) {
        val tileSize = maxWidth / BOARD_SIZE

        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, Color.Red)
        )

        Box(
            Modifier
                .offset(
                    x = tileSize * (state.food?.first ?: 5),
                    y = tileSize * (state.food?.second ?: 5)
                )
                .size(tileSize)
                .background(
                    Color.Red, CircleShape
                )
        )

        state.snake.forEach {
            Box(
                modifier = Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(
                        Color.Red
                    )
            )
        }
    }
}

@Preview
@Composable
private fun GameScreenPreview() {
    SnakeGameTheme {
        GameScreen(
            state = GameState(),
            onAction = {}
        )
    }
}