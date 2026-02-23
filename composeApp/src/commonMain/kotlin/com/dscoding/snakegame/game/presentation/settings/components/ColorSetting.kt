package com.dscoding.snakegame.game.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dscoding.snakegame.core.presentation.theme.SnakeGameTheme
import com.dscoding.snakegame.game.presentation.settings.models.ColorUi
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.food_color


@Composable
fun ColorSettings(
    title: String,
    selectedColor: ColorUi,
    onColorSelected: (ColorUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = White
        )
        Spacer(modifier = Modifier.height(6.dp))
        FlowRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.spacedBy(6.dp),
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            ColorUi.entries.forEach { colorUi ->
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorUi.color)
                        .border(
                            width = 2.dp,
                            color = if (selectedColor == colorUi) {
                                White
                            } else Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            onColorSelected(colorUi)
                        }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ColorSettingsPreview() {
    SnakeGameTheme {
        ColorSettings(
            title = stringResource(Res.string.food_color),
            selectedColor = ColorUi.BRIGHT_YELLOW,
            onColorSelected = { },
            modifier = Modifier.padding(100.dp)
        )
    }
}
