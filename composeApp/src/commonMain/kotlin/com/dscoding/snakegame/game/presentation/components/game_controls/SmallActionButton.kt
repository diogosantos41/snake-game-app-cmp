package com.dscoding.snakegame.game.presentation.components.game_controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.ContainerBorderWhite
import com.dscoding.snakegame.core.presentation.theme.ContainerBackgroundBlack
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerBorderWidth
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun SmallActionButton(
    icon: ImageVector,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Button(
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(size = ContainerRoundedCornerShapeSize),
        colors = ButtonDefaults.buttonColors(
            containerColor = ContainerBackgroundBlack,
            disabledContainerColor = ContainerBackgroundBlack
        ),
        border = BorderStroke(width = ContainerBorderWidth, color = ContainerBorderWhite),
        modifier = modifier.size(80.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = White,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Preview
@Composable
private fun SmallActionButtonPreview() {
    SnakeGameTheme {
        SmallActionButton(
            icon = Icons.Default.Pause,
            enabled = true,
            onClick = {}
        )
    }
}
