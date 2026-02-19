package com.dscoding.snakegame.game.presentation.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.ContainerBackgroundBlack
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerBorderWidth
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize
import com.dscoding.snakegame.core.presentation.theme.GameOrange
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = ContainerBackgroundBlack,
            contentColor = White
        ),
        border = BorderStroke(width = ContainerBorderWidth, color = GameOrange),
        shape = RoundedCornerShape(size = ContainerRoundedCornerShapeSize),
        modifier = modifier
            .widthIn(max = 400.dp)
            .height(60.dp),
    ) {
        Text(
            text = text.uppercase(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
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