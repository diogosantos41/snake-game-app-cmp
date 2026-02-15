package com.dscoding.snakegame.game.presentation.components.game_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.blackAlphaGradient
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.high_score
import snakegame.composeapp.generated.resources.score

@Composable
fun GameControls(
    score: Int,
    highscore: Int,
    showDirectionPad: Boolean,
    onDirectionClick: (movementDirection: MovementDirection) -> Unit,
    onPauseClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = blackAlphaGradient
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ScoreChip(
                    title = stringResource(Res.string.score),
                    value = score
                )
                ScoreChip(
                    title = stringResource(Res.string.high_score),
                    value = highscore
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SmallActionButton(
                    icon = Icons.Default.Tune,
                    onClick = onSettingsClick
                )
                SmallActionButton(
                    icon = Icons.Default.Pause,
                    onClick = onPauseClick
                )
            }
        }
        if (showDirectionPad) {
            DirectionPad(
                onDirectionClick = onDirectionClick,
                modifier = Modifier.offset(y = (-25).dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun GameControlsPreview() {
    SnakeGameTheme {
        GameControls(
            score = 10,
            highscore = 20,
            showDirectionPad = true,
            onDirectionClick = {},
            onPauseClick = {},
            onSettingsClick = {},
            modifier = Modifier.height(500.dp)
        )
    }
}