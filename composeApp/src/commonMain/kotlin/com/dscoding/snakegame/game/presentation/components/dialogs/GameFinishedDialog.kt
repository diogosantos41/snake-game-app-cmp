package com.dscoding.snakegame.game.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.components.GameDialogContent
import com.dscoding.snakegame.core.presentation.components.GameDialogHeader
import com.dscoding.snakegame.core.presentation.components.GameStat
import com.dscoding.snakegame.core.presentation.theme.Dimens.HorizontalSpacingDialogComponent
import com.dscoding.snakegame.core.presentation.theme.Dimens.VerticalSpacingBetweenDialogComponent
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.alphaVerticalGradient
import com.dscoding.snakegame.core.presentation.util.tileGridBackground
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import com.dscoding.snakegame.game.presentation.GameViewModel.Companion.BOARD_SIZE
import com.dscoding.snakegame.game.presentation.components.ActionButton
import com.dscoding.snakegame.game.presentation.components.game_board.GameBoard
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.game_over
import snakegame.composeapp.generated.resources.high_score
import snakegame.composeapp.generated.resources.play_again
import snakegame.composeapp.generated.resources.score
import snakegame.composeapp.generated.resources.share

@Composable
fun GameFinishedDialog(
    finalScore: Int,
    finalFood: Pair<Int, Int>?,
    finalSnake: List<Pair<Int, Int>>,
    finalMovementDirection: MovementDirection,
    highScore: Int,
    onPlayAgainClick: () -> Unit,
    onShareClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GameDialogContent(
        onDismiss = onDismiss,
        dismissOnBackPress = true,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = VerticalSpacingBetweenDialogComponent,
                    horizontal = HorizontalSpacingDialogComponent
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(VerticalSpacingBetweenDialogComponent)
        ) {
            GameDialogHeader(
                title = stringResource(Res.string.game_over),
                showClose = false,
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()
            GameStat(
                title = stringResource(Res.string.score),
                statValue = "$finalScore"
            )
            GameStat(
                title = stringResource(Res.string.high_score),
                statValue = "$highScore"
            )
            BoxWithConstraints(
                modifier = Modifier
                    .background(
                        alphaVerticalGradient(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    .border(width = 1.dp, color = White)
            ) {
                val tileSize = maxWidth / BOARD_SIZE
                GameBoard(
                    food = finalFood,
                    snake = finalSnake,
                    currentMovementDirection = finalMovementDirection,
                    isFoodAnimated = false,
                    modifier = Modifier.tileGridBackground(tileSize = tileSize)
                )
            }
            ActionButton(
                text = stringResource(Res.string.share),
                onClick = onShareClick,
                modifier = Modifier.fillMaxWidth()
            )
            ActionButton(
                text = stringResource(Res.string.play_again),
                onClick = onPlayAgainClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun GameFinishedDialogPreview() {
    SnakeGameTheme {
        Box(modifier = Modifier.padding(all = 150.dp)) {
            GameFinishedDialog(
                finalScore = 12,
                finalFood = 2 to 3,
                finalSnake = (1..6).map { it to 1 },
                finalMovementDirection = MovementDirection.LEFT,
                highScore = 49,
                onPlayAgainClick = {},
                onShareClick = {},
                onDismiss = {}
            )
        }
    }
}