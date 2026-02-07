@file:OptIn(ExperimentalMaterial3Api::class)

package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.GameDarkOrange
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun GameToolbar(
    currentScore: Int,
    highScore: Int,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 20.dp,
            alignment = Alignment.End
        ),
        modifier = modifier
            .background(GameDarkOrange)
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        ScoreChip(
            icon = "🍓",
            value = currentScore
        )
        ScoreChip(
            icon = "🏆",
            value = highScore
        )
        IconButton(onClick = onSettingsClick) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = "Settings",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun GameToolbarPreview() {
    SnakeGameTheme {
        GameToolbar(
            currentScore = 10,
            highScore = 200,
            onSettingsClick = {},
        )
    }
}