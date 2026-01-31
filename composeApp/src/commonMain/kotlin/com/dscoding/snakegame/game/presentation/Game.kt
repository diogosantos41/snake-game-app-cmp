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
import androidx.compose.foundation.layout.width
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
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.Violet
import com.dscoding.snakegame.game.presentation.GameViewModel.Companion.BOARD_SIZE
import com.dscoding.snakegame.game.presentation.models.SnakeDirection
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameRoot(
    viewModel: GameViewModel = koinViewModel()
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = { onAction(GameAction.OnDirectionClick(SnakeDirection.UP)) },
                modifier = Modifier.size(150.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowUp, null)
            }
            Row {
                Button(
                    onClick = { onAction(GameAction.OnDirectionClick(SnakeDirection.LEFT)) },
                    modifier = Modifier.size(150.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, null)
                }
                Spacer(modifier = Modifier.width(80.dp))
                Button(
                    onClick = { onAction(GameAction.OnDirectionClick(SnakeDirection.RIGHT)) },
                    modifier = Modifier.size(150.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, null)
                }
            }
            Button(
                onClick = { onAction(GameAction.OnDirectionClick(SnakeDirection.DOWN)) },
                modifier = Modifier.size(150.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
        }
    }
}

@Composable
fun Board(state: GameState) {
    BoxWithConstraints(Modifier.padding(12.dp)) {
        val tileSize = maxWidth / BOARD_SIZE

        Box(
            modifier = Modifier
                .size(maxWidth)
                .border(2.dp, Color.Gray)
        )


        state.food?.let { food ->
            Box(
                modifier = Modifier
                    .offset(
                        x = tileSize * (food.first),
                        y = tileSize * (food.second)
                    )
                    .size(tileSize)
                    .background(
                        Color.Red, CircleShape
                    )
            )
        }

        state.snake.forEachIndexed { index, body ->
            Box(
                modifier = Modifier
                    .offset(x = tileSize * body.first, y = tileSize * body.second)
                    .size(tileSize)
                    .padding(all = 0.2.dp)
                    .background(
                        if(index == 0) Color.Blue else Violet
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