package com.dscoding.snakegame.game.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
expect fun isOrientationLandscape(): State<Boolean>