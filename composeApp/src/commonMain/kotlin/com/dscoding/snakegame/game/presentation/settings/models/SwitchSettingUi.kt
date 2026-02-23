package com.dscoding.snakegame.game.presentation.settings.models

import com.dscoding.snakegame.core.presentation.util.UiText

data class SwitchSettingUi(
    val title: UiText = UiText.DynamicString(""),
    val checked: Boolean = false,
    val checkedText: UiText = UiText.DynamicString(""),
    val uncheckedText: UiText = UiText.DynamicString(""),
) {
    val valueText: UiText get() = if (checked) checkedText else uncheckedText
}