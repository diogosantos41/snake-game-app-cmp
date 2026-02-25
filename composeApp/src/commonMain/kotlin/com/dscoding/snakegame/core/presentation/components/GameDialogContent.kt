package com.dscoding.snakegame.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dscoding.snakegame.core.presentation.theme.ContainerBorderWhite
import com.dscoding.snakegame.core.presentation.theme.DialogBackgroundBlack
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerBorderWidth
import com.dscoding.snakegame.core.presentation.theme.Dimens.ContainerRoundedCornerShapeSize

@Composable
fun GameDialogContent(
    onDismiss: () -> Unit,
    dismissOnClickOutside: Boolean = false,
    dismissOnBackPress: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val dialogShape = RoundedCornerShape(size = ContainerRoundedCornerShapeSize)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = dismissOnClickOutside,
            dismissOnBackPress = dismissOnBackPress
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .clip(dialogShape),
            color = DialogBackgroundBlack,
            shape = dialogShape,
            border = BorderStroke(
                width = ContainerBorderWidth,
                color = ContainerBorderWhite,
            )
        ) {
            content()
        }
    }
}