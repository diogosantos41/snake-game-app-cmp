package com.dscoding.snakegame.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun GameStat(
    title: String,
    statValue: String,
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = MaterialTheme.typography.bodyMedium.toSpanStyle()
            ) {
                append("$title: ")
            }
            withStyle(
                style = MaterialTheme.typography.labelMedium.toSpanStyle()
            ) {
                append(statValue)
            }
        },
        textAlign = TextAlign.Center
    )
}