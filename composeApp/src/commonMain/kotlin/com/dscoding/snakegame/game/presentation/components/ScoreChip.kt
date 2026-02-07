package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme


@Composable
fun ScoreChip(
    icon: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White.copy(alpha = 0f),
        shape = RoundedCornerShape(1.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Text(
                text = icon,
                fontSize = 16.sp
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "$value",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = Bold)
            )
        }
    }
}

@Preview
@Composable
fun ScoreChipPreview() {
    SnakeGameTheme {
        ScoreChip(
            icon = "🏆",
            value = 200,
            modifier = Modifier.padding(5.dp)
        )
    }
}