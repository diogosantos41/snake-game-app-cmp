package com.dscoding.snakegame.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.snakegame.core.presentation.designsystem.StartGameDialog
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.orangeAlphaGradient
import com.dscoding.snakegame.core.presentation.util.tileGridBackground
import com.dscoding.snakegame.game.domain.models.MovementDirection
import com.dscoding.snakegame.game.presentation.GameViewModel.Companion.BOARD_SIZE
import com.dscoding.snakegame.game.presentation.components.game_board.GameBoard
import com.dscoding.snakegame.game.presentation.components.game_controls.GameControls
import com.dscoding.snakegame.game.presentation.models.ControlMode
import com.dscoding.snakegame.game.presentation.models.PlayState
import com.dscoding.snakegame.game.presentation.utils.snakeSwipeControls
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import snakegame.composeapp.generated.resources.Res
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
                )
                .snakeSwipeControls(
                    enabled = state.movementControlMode == ControlMode.SWIPE
                ) { direction ->
                    onAction(GameAction.OnDirectionClick(direction))
                },
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
                GameBoard(
                    food = state.food,
                    snake = state.snake,
                    currentMovementDirection = state.currentMovementDirection
                )
                GameControls(
                    score = state.score,
                    highscore = state.highScore,
                    showDirectionPad = state.movementControlMode == ControlMode.BUTTONS,
                    onDirectionClick = {
                        onAction(GameAction.OnDirectionClick(it))
                    },
                    onPauseClick = {},
                    onSettingsClick = {}
                )
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
}

@Preview
@Composable
private fun GameScreenPreview() {
    SnakeGameTheme {
        GameScreen(
            state = GameState(
                currentPlayState = PlayState.PLAYING,
                movementControlMode = ControlMode.BUTTONS,
                score = 20,
                highScore = 200,
                food = 10 to 3,
                snake = (1..8).map { it to 5 },
                currentMovementDirection = MovementDirection.LEFT,
            ),
            onAction = {}
        )
    }
}