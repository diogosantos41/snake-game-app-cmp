package com.dscoding.snakegame.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ScreenRotation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.core.presentation.util.tileGridBackground
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.rotate_device_to_continue

@Composable
fun PortraitGuard(
    isLandscape: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (isLandscape) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Black)
                .tileGridBackground(
                    tileSize = 25.dp,
                    lineColor = White.copy(alpha = 0.3f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ScreenRotation,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .size(50.dp)
                )
                Text(
                    text = stringResource(Res.string.rotate_device_to_continue),
                    color = White
                )
            }
        }
    } else {
        content()
    }
}

@Preview(
    device = "spec:width=411dp,height=891dp,orientation=landscape,dpi=420",
    showSystemUi = true,
)
@Composable
private fun PortraitGuardPreview() {
    SnakeGameTheme {
        PortraitGuard(isLandscape = true, content = {})
    }
}