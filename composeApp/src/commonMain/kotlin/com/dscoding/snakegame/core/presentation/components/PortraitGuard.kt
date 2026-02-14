package com.dscoding.snakegame.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp

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
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Please rotate back to portrait",
                    style = MaterialTheme.typography.titleMedium,
                    color = Black
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Snake is only available in portrait mode.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black
                )
            }
        }
    } else {
        content()
    }
}