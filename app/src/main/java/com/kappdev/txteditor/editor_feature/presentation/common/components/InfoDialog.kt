package com.kappdev.txteditor.editor_feature.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kappdev.txteditor.editor_feature.presentation.editor.Dialog
import com.kappdev.txteditor.editor_feature.presentation.common.components.Dialog.Platform as DialogPlatform
import com.kappdev.txteditor.editor_feature.presentation.common.components.Dialog.Title as DialogTitle
import com.kappdev.txteditor.editor_feature.presentation.common.components.Dialog.Text as DialogText
import com.kappdev.txteditor.editor_feature.presentation.common.components.Dialog.Button as DialogButton

@Composable
fun InfoDialog(
    data: Dialog,
    onDismiss: () -> Unit,
    onNegative: () -> Unit = {},
    onPositive: () -> Unit
) {
    fun dismissAndCall(lambda: () -> Unit) {
        onDismiss()
        lambda()
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        DialogPlatform {
            data.titleRes?.let { titleRes ->
                DialogTitle(
                    text = stringResource(titleRes),
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            data.messageRes?.let { messageRes ->
                DialogText(
                    text = stringResource(messageRes),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            VerticalSpace(8.dp)

            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.Bottom
            ) {
                DialogButton(
                    title = stringResource(data.negativeButtonRes),
                    onClick = { dismissAndCall(onNegative) }
                )

                DialogButton(
                    title = stringResource(data.positiveButtonRes),
                    onClick = { dismissAndCall(onPositive) }
                )
            }
        }
    }
}