package com.dscoding.snakegame.core.domain.models

import androidx.compose.ui.graphics.Color
import com.dscoding.snakegame.core.presentation.theme.GameBlue
import com.dscoding.snakegame.core.presentation.theme.GameGreen
import com.dscoding.snakegame.core.presentation.theme.GameOrange
import com.dscoding.snakegame.core.presentation.theme.GamePink
import com.dscoding.snakegame.core.presentation.theme.GamePurple

enum class ColorUi(
    val color: Color,
) {
    BURNT_ORANGE(color = GameOrange),
    PINK(color = GamePink),
    PURPLE(color = GamePurple),
    BLUE(color = GameBlue),
    RETRO_GREEN(color = GameGreen),
}