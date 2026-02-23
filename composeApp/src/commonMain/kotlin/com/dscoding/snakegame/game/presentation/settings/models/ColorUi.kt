package com.dscoding.snakegame.game.presentation.settings.models

import androidx.compose.ui.graphics.Color
import com.dscoding.snakegame.core.presentation.theme.GameBlue
import com.dscoding.snakegame.core.presentation.theme.GameGreen
import com.dscoding.snakegame.core.presentation.theme.GameOrange
import com.dscoding.snakegame.core.presentation.theme.GamePink
import com.dscoding.snakegame.core.presentation.theme.GamePurple
import com.dscoding.snakegame.core.presentation.theme.GameYellow
import com.dscoding.snakegame.core.presentation.util.UiText
import snakegame.composeapp.generated.resources.Res
import snakegame.composeapp.generated.resources.color_blue
import snakegame.composeapp.generated.resources.color_bright_yellow
import snakegame.composeapp.generated.resources.color_burnt_orange
import snakegame.composeapp.generated.resources.color_pink
import snakegame.composeapp.generated.resources.color_purple
import snakegame.composeapp.generated.resources.color_retro_green

enum class ColorUi(
    val color: Color,
    val title: UiText
) {

    BURNT_ORANGE(
        color = GameOrange,
        title = UiText.Resource(Res.string.color_burnt_orange)
    ),
    PINK(
        color = GamePink,
        title = UiText.Resource(Res.string.color_pink)
    ),

    PURPLE(
        color = GamePurple,
        title = UiText.Resource(Res.string.color_purple)
    ),

    BLUE(
        color = GameBlue,
        title = UiText.Resource(Res.string.color_blue)
    ),

    RETRO_GREEN(
        color = GameGreen,
        title = UiText.Resource(Res.string.color_retro_green)
    ),

    BRIGHT_YELLOW(
        color = GameYellow,
        title = UiText.Resource(Res.string.color_bright_yellow)
    )
}