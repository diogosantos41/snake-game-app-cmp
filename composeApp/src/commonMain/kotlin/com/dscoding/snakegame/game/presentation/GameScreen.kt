package com.dscoding.snakegame.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dscoding.snakegame.core.presentation.components.PortraitGuard
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.orangeAlphaGradient
import com.dscoding.snakegame.core.presentation.util.DialogScopedViewModel
import com.dscoding.snakegame.core.presentation.util.tileGridBackground
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import com.dscoding.snakegame.game.presentation.GameViewModel.Companion.BOARD_SIZE
import com.dscoding.snakegame.game.presentation.components.GamePausedDialog
import com.dscoding.snakegame.game.presentation.components.StartCountdown
import com.dscoding.snakegame.game.presentation.components.StartGameDialog
import com.dscoding.snakegame.game.presentation.components.game_board.GameBoard
import com.dscoding.snakegame.game.presentation.components.game_controls.GameControls
import com.dscoding.snakegame.game.presentation.models.ControlMode
import com.dscoding.snakegame.game.presentation.models.PausedState
import com.dscoding.snakegame.game.presentation.models.PlayState
import com.dscoding.snakegame.game.presentation.settings.SettingsRoot
import com.dscoding.snakegame.game.presentation.utils.isAppInForeground
import com.dscoding.snakegame.game.presentation.utils.isOrientationLandscape
import com.dscoding.snakegame.game.presentation.utils.snakeSwipeControls
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameRoot(
    viewModel: GameViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val isAppInForeground by isAppInForeground()
    val isOrientationLandscape by isOrientationLandscape()

    // TODO [BUG] background on countdown, games resumes anyway
    // TODO [BUG] iOS Rotations creates a offset effect on the dialog
    LaunchedEffect(isAppInForeground, isOrientationLandscape, state.currentPlayState) {
        val shouldPause =
            state.currentPlayState is PlayState.Playing &&
                    (!isAppInForeground || isOrientationLandscape)

        if (shouldPause) {
            viewModel.onAction(GameAction.OnPauseGameClick)
        }
    }

    PortraitGuard(isLandscape = isOrientationLandscape) {
        GameScreen(
            state = state,
            onAction = viewModel::onAction
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
                .background(brush = orangeAlphaGradient)
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
                    isGameplayInputEnabled = state.currentPlayState is PlayState.Playing,
                    onDirectionClick = {
                        onAction(GameAction.OnDirectionClick(it))
                    },
                    onPauseClick = { onAction(GameAction.OnPauseGameClick) },
                    onSettingsClick = { onAction(GameAction.OnSettingsClick) }
                )
            }
        }

        state.countdownSecondsRemaining?.let {
            StartCountdown(
                secondsRemaining = "$it",
                modifier = Modifier.fillMaxSize()
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

        if (state.currentPlayState is PlayState.ReadyToPlay
            || state.currentPlayState is PlayState.Finished
        ) {
            StartGameDialog(
                onStartGameClick = { onAction(GameAction.OnStartGameClick) },
                onSettingsClick = { onAction(GameAction.OnSettingsClick) },
                onDismiss = { onAction(GameAction.OnStartGameClick) }
            )
        }

        DialogScopedViewModel(
            visible = state.currentPlayState == PlayState.Paused(PausedState.SETTINGS)
        ) {
            SettingsRoot(onDismiss = { onAction(GameAction.OnSettingsDismissClick) })

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