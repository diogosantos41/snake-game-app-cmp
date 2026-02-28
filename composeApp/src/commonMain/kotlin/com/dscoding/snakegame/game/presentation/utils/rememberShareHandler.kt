package com.dscoding.snakegame.game.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.stringResource
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.share_message

@Composable
fun rememberShareHandler(
    score: Int,
    url: String,
    onClick: (String) -> Unit
): () -> Unit {
    val shareMessage = stringResource(
        Res.string.share_message,
        score.toString(),
        url
    )

    return remember(score) {
        { onClick(shareMessage) }
    }
}