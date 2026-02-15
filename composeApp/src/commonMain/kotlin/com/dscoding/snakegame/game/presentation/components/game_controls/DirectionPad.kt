package com.dscoding.snakegame.game.presentation.components.game_controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.domain.engine.models.MovementDirection


@Composable
fun DirectionPad(
    onDirectionClick: (movementDirection: MovementDirection) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy((-20).dp)
    ) {
        DiamondDirectionButton(
            onClick = { onDirectionClick(MovementDirection.UP) },
            icon = Icons.Default.KeyboardArrowUp,
        )
        Row {
            DiamondDirectionButton(
                onClick = { onDirectionClick(MovementDirection.LEFT) },
                icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            )
            Spacer(modifier = Modifier.width(60.dp))
            DiamondDirectionButton(
                onClick = { onDirectionClick(MovementDirection.RIGHT) },
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            )
        }
        DiamondDirectionButton(
            onClick = { onDirectionClick(MovementDirection.DOWN) },
            icon = Icons.Default.KeyboardArrowDown,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DirectionPadPreview() {
    SnakeGameTheme {
        DirectionPad(
            onDirectionClick = {}
        )
    }
}