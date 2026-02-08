package com.dscoding.snakegame.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.snakegame.core.presentation.designsystem.StartGameDialog
import com.dscoding.snakegame.core.presentation.theme.GameYellow
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.blackAlphaGradient
import com.dscoding.snakegame.core.presentation.theme.orangeAlphaGradient
import com.dscoding.snakegame.core.presentation.util.tileGridBackground
import com.dscoding.snakegame.game.domain.models.MovementDirection
import com.dscoding.snakegame.game.presentation.GameViewModel.Companion.BOARD_SIZE
import com.dscoding.snakegame.game.presentation.components.DiamondDirectionButton
import com.dscoding.snakegame.game.presentation.components.ScoreChip
import com.dscoding.snakegame.game.presentation.components.SnakeHead
import com.dscoding.snakegame.game.presentation.models.PlayState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.high_score
import snakegame.composeapp.generated.resources.score
import snakegame.composeapp.generated.resources.snake_game

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
    Scaffold { paddingValues ->

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = orangeAlphaGradient)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 0.dp
                ),
        ) {
            val tileSize = maxWidth / BOARD_SIZE

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .tileGridBackground(
                        tileSize = tileSize,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Board(state, onAction)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            brush = blackAlphaGradient
                        )
                        .padding(top = 40.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy((-20).dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ScoreChip(
                            title = stringResource(Res.string.score),
                            value = state.score
                        )
                        DiamondDirectionButton(
                            onClick = { onAction(GameAction.OnDirectionClick(MovementDirection.UP)) },
                            icon = Icons.Default.KeyboardArrowUp,
                        )
                        ScoreChip(
                            title = stringResource(Res.string.high_score),
                            value = state.highScore
                        )
                    }
                    Row {
                        DiamondDirectionButton(
                            onClick = { onAction(GameAction.OnDirectionClick(MovementDirection.LEFT)) },
                            icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        )
                        Spacer(modifier = Modifier.width(60.dp))
                        DiamondDirectionButton(
                            onClick = { onAction(GameAction.OnDirectionClick(MovementDirection.RIGHT)) },
                            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        )
                    }
                    DiamondDirectionButton(
                        onClick = { onAction(GameAction.OnDirectionClick(MovementDirection.DOWN)) },
                        icon = Icons.Default.KeyboardArrowDown,
                    )
                    Spacer(Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black.copy(
                                    alpha = 0.5f
                                )
                            ),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black.copy(
                                    alpha = 0.5f
                                )
                            ),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Board(state: GameState, onAction: (GameAction) -> Unit) {
    BoxWithConstraints {
        val tileSize = maxWidth / BOARD_SIZE

        Box(
            modifier = Modifier
                .size(maxWidth)
                .border(0.3.dp, White.copy(alpha = 0.3f))
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
                        GameYellow, CircleShape
                    )
            )
        }
        state.snake.forEachIndexed { index, body ->
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
                    movementDirection = state.currentMovementDirection
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
    if (state.currentPlayState == PlayState.READY_TO_PLAY
        || state.currentPlayState == PlayState.FINISHED
    ) {
        StartGameDialog(
            title = stringResource(Res.string.snake_game),
            onStartGameClick = { onAction(GameAction.OnGameStarted) },
            onDismiss = { onAction(GameAction.OnGameStarted) }
        )
    }
}

@Preview
@Composable
private fun GameScreenPreview() {
    SnakeGameTheme {
        GameScreen(
            state = GameState(
                currentPlayState = PlayState.PLAYING,
                score = 20,
                highScore = 200
            ),
            onAction = {}
        )
    }
}