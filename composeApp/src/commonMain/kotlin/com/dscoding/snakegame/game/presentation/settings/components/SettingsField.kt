package com.dscoding.snakegame.game.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun SettingsField(
    title: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(
                onClick = onClick
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = White
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Gray,
        )
    }
}

@Preview
@Composable
private fun SettingsFieldPreview() {
    SnakeGameTheme {
        Column(
            Modifier.fillMaxWidth().padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SettingsField(
                title = "Title here",
                value = "Value here",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            SettingsField(
                title = "Title here",
                value = "Value here",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}