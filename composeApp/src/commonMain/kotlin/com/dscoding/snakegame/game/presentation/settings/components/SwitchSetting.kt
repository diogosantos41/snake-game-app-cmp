package com.dscoding.snakegame.game.presentation.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme

@Composable
fun SwitchSetting(
    title: String,
    value: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
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
        Switch(
            checked = enabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = Gray,
                checkedTrackColor = Gray,
                uncheckedTrackColor = LightGray,
            ),
        )
    }
}

@Preview
@Composable
private fun SwitchSettingPreview() {
    SnakeGameTheme {
        Column(
            Modifier.fillMaxWidth().padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SwitchSetting(
                title = "Title here",
                value = "Value here",
                enabled = true,
                onToggle = {},
                modifier = Modifier.fillMaxWidth()
            )
            SwitchSetting(
                title = "Title here",
                value = "Value here",
                enabled = false,
                onToggle = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}