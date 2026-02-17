package com.dscoding.snakegame.game.presentation.components.game_controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.ContainerBorderWhite
import com.dscoding.snakegame.core.presentation.theme.ContainerBackgroundBlack
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerBorderWidth
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme


@Composable
fun ScoreChip(
    title: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(4.dp))

        Surface(
            shape = RoundedCornerShape(10.dp),
            color = ContainerBackgroundBlack,
            border = BorderStroke(
                width = ContainerBorderWidth,
                color = ContainerBorderWhite
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(width = 80.dp, height = 44.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = value.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview
@Composable
fun ScoreChipPreview() {
    SnakeGameTheme {
        ScoreChip(
            title = "Score",
            value = 200,
            modifier = Modifier.padding(5.dp)
        )
    }
}