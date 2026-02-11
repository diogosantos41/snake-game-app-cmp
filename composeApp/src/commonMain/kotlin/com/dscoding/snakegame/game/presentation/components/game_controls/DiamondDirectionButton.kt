package com.dscoding.snakegame.game.presentation.components.game_controls

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun DiamondDirectionButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    iconSize: Dp = 60.dp,
    containerColor: Color = Color.Black.copy(alpha = 0.5f),
    iconTint: Color = Color.White,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier.size(size).graphicsLayer { rotationZ = 45f }) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(iconSize).graphicsLayer { rotationZ = -45f })
    }
}

@Preview
@Composable
private fun DiamondDirectionButtonPreview() {
    SnakeGameTheme {
        DiamondDirectionButton(
            icon = Icons.Default.KeyboardArrowRight,
            onClick = {},
            modifier = Modifier.padding(30.dp)
        )
    }
}