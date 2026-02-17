package com.dscoding.snakegame.core.presentation.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.Retron2000

object Fonts {
    val Retron
        @Composable get() = FontFamily(
            Font(
                resource = Res.font.Retron2000,
                weight = FontWeight.Normal
            )
        )
}

val Typography: Typography
    @Composable get() = Typography(
        // Body
        bodyMedium = TextStyle(
            fontFamily = Fonts.Retron,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = White
        ),
        // Body Bold
        labelMedium = TextStyle(
            fontFamily = Fonts.Retron,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = White
        ),
        // Smaller Titles
        titleSmall = TextStyle(
            fontFamily = Fonts.Retron,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = 2.sp,
            color = White

        ),
        // Titles / Headers
        titleMedium = TextStyle(
            fontFamily = Fonts.Retron,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            color = White
        ),
        // Buttons
        headlineSmall = TextStyle(
            fontFamily = Fonts.Retron,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = 2.sp,
            color = White
        ),
    )

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun TypographyPreview() {
    SnakeGameTheme {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(text = "body medium", style = MaterialTheme.typography.bodyMedium)
            Text(text = "label medium", style = MaterialTheme.typography.labelMedium)
            Text(text = "title small", style = MaterialTheme.typography.titleSmall)
            Text(text = "title medium", style = MaterialTheme.typography.titleMedium)
            Text(text = "headline small", style = MaterialTheme.typography.headlineSmall)
        }
    }
}