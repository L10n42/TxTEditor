package com.kappdev.txteditor.editor_feature.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kappdev.txteditor.editor_feature.presentation.editor.Dialog
import com.kappdev.txteditor.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoDialog(
    data: Dialog,
    onDismiss: () -> Unit,
    onNegative: () -> Unit = {},
    onPositive: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    DialogComponents.Title(
                        text = stringResource(data.titleRes),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, top = 16.dp, end = 4.dp)
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(top = 4.dp, end = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Dismiss dialog button"
                        )
                    }
                }

                DialogComponents.Text(
                    text = stringResource(data.messageRes),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(bottom = 16.dp, top = 32.dp, end = 16.dp, start = 16.dp)
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNegative(); onDismiss() }
                    ) {
                        Text(
                            text = stringResource(data.negativeButtonRes)
                        )
                    }

                    Button(
                        onClick = { onPositive(); onDismiss() }
                    ) {
                        Text(
                            text = stringResource(data.positiveButtonRes)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun InfoDialogPreview() {
    AppTheme {
        InfoDialog(
            data = Dialog.SaveAndNew,
            onDismiss = { /*TODO*/ },
            onPositive = { /*TODO*/ }
        )
    }
}