package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.theme.Violet

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(size = 15.dp),
        modifier = modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
            .height(70.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Violet,
            contentColor = White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text.uppercase(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    SnakeGameTheme {
        ActionButton(
            text = "Start Game",
            onClick = {},
            modifier = Modifier
                .padding(20.dp)
        )
    }
}