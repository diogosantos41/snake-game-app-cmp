package com.dscoding.snakegame.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.snakegame.core.presentation.components.MobileLandscapeGuard
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.alphaVerticalGradient
import com.dscoding.snakegame.core.presentation.util.DialogScopedViewModel
import com.dscoding.snakegame.core.presentation.util.rememberRootBackAction
import com.dscoding.snakegame.core.presentation.util.tileGridBackground
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import com.dscoding.snakegame.game.presentation.GameViewModel.Companion.BOARD_SIZE
import com.dscoding.snakegame.game.presentation.components.StartCountdown
import com.dscoding.snakegame.game.presentation.components.dialogs.GameFinishedDialog
import com.dscoding.snakegame.game.presentation.components.dialogs.GamePausedDialog
import com.dscoding.snakegame.game.presentation.components.dialogs.GameStartDialog
import com.dscoding.snakegame.game.presentation.components.game_board.GameBoard
import com.dscoding.snakegame.game.presentation.components.game_controls.GameControls
import com.dscoding.snakegame.game.presentation.models.ControlMode
import com.dscoding.snakegame.game.presentation.models.PausedState
import com.dscoding.snakegame.game.presentation.models.PlayState
import com.dscoding.snakegame.game.presentation.settings.SettingsRoot
import com.dscoding.snakegame.game.presentation.utils.isAppInForeground
import com.dscoding.snakegame.game.presentation.utils.isLandscapeMobile
import com.dscoding.snakegame.game.presentation.utils.rememberShareHandler
import com.dscoding.snakegame.game.presentation.utils.snakeSwipeControls
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.share_url

@Composable
fun GameRoot(
    viewModel: GameViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    val rootBackAction = rememberRootBackAction()
    val isAppInForeground by isAppInForeground()
    val isLandscapeMobile by isLandscapeMobile()

    LaunchedEffect(isAppInForeground, isLandscapeMobile) {
        if (!isAppInForeground || isLandscapeMobile) {
            viewModel.onAction(GameAction.OnInvalidAppState)
        }
    }

    MobileLandscapeGuard(isLandscapeMobile = isLandscapeMobile) {
        GameScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    is GameAction.GoBack -> rootBackAction()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
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
                .background(
                    brush = alphaVerticalGradient(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 0.dp
                )
                .snakeSwipeControls(
                    enabled = state.movementControlMode == ControlMode.SWIPE
                            && state.currentPlayState is PlayState.Playing
                ) { direction ->
                    onAction(GameAction.OnDirectionClick(direction))
                },
        ) {
            val minControlsHeight = 400.dp
            val boardSize = minOf(maxWidth, maxHeight - minControlsHeight).coerceAtLeast(0.dp)
            val tileSize = (boardSize / BOARD_SIZE).coerceAtLeast(1.dp)

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(boardSize)
                        .tileGridBackground(tileSize = tileSize)
                        .border(1.dp, White.copy(alpha = 0.5f))
                ) {
                    GameBoard(
                        food = state.food,
                        snake = state.snake,
                        currentMovementDirection = state.currentMovementDirection,
                        isFoodAnimated = state.currentPlayState is PlayState.Playing,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                GameControls(
                    score = state.score,
                    highscore = state.highScore,
                    showDirectionPad = state.movementControlMode == ControlMode.BUTTONS,
                    isGameplayInputEnabled = state.currentPlayState is PlayState.Playing,
                    onDirectionClick = {
                        onAction(GameAction.OnDirectionClick(it))
                    },
                    onPauseClick = { onAction(GameAction.OnPauseGameClick) },
                    onSettingsClick = { onAction(GameAction.OnSettingsClick) },
                    modifier = Modifier.tileGridBackground(tileSize = tileSize)
                )
            }
        }

        if (state.currentPlayState is PlayState.ReadyToPlay) {
            GameStartDialog(
                onStartGameClick = { onAction(GameAction.OnStartGameClick) },
                onSettingsClick = { onAction(GameAction.OnSettingsClick) },
                onDismiss = { onAction(GameAction.GoBack) }
            )
        }

        if (state.currentPlayState == PlayState.Paused(PausedState.MENU)) {
            GamePausedDialog(
                currentScore = state.score,
                onResumeClick = { onAction(GameAction.OnResumeGameClick) },
                onRestartClick = { onAction(GameAction.OnRestartGameClick) },
                onSettingsClick = { onAction(GameAction.OnSettingsClick) },
                onDismiss = { onAction(GameAction.OnResumeGameClick) },
            )
        }

        DialogScopedViewModel(
            visible = state.currentPlayState == PlayState.Paused(PausedState.SETTINGS)
        ) {
            SettingsRoot(onDismiss = { onAction(GameAction.OnSettingsDialogDismiss) })
        }

        state.countdownSecondsRemaining?.let {
            StartCountdown(
                secondsRemaining = "$it",
                modifier = Modifier.fillMaxSize()
            )
        }

        if (state.currentPlayState is PlayState.Finished) {

            val onShareClick =
                rememberShareHandler(
                    score = state.score,
                    url = stringResource(Res.string.share_url)
                ) { shareMessage ->
                    onAction(
                        GameAction.OnShareResultClick(
                            shareMessage = shareMessage
                        )
                    )
                }

            GameFinishedDialog(
                finalScore = state.score,
                finalFood = state.food,
                finalSnake = state.snake,
                finalMovementDirection = state.currentMovementDirection,
                highScore = state.highScoreAtGameEnd ?: state.highScore,
                onPlayAgainClick = { onAction(GameAction.OnRestartGameClick) },
                onShareClick = onShareClick,
                onDismiss = { onAction(GameAction.OnFinishedDialogDismiss) },
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
                currentPlayState = PlayState.Playing,
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