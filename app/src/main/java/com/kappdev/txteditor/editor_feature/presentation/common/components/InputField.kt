package com.kappdev.txteditor.editor_feature.presentation.common.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    value: String,
    modifier: Modifier = Modifier,
    hint: String = "",
    enable: Boolean = true,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
    onActionClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        enabled = enable,
        singleLine = singleLine,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        keyboardActions = KeyboardActions {
            onActionClick()
        },
        placeholder = {
            Text(text = hint)
        },
        shape = RoundedCornerShape(8.dp),
        colors = textFieldColors(),
        modifier = modifier
    )
}

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onBackground,
    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
    disabledTextColor = MaterialTheme.colorScheme.onSurface,
    errorTextColor = MaterialTheme.colorScheme.error,

    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
    errorPlaceholderColor = MaterialTheme.colorScheme.onSurface,

    errorIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,

    focusedContainerColor = MaterialTheme.colorScheme.background,
    unfocusedContainerColor = MaterialTheme.colorScheme.background,
    disabledContainerColor = MaterialTheme.colorScheme.background,
    errorContainerColor = MaterialTheme.colorScheme.background,

    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
    errorLeadingIconColor = MaterialTheme.colorScheme.onSurface,

    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
    errorTrailingIconColor = MaterialTheme.colorScheme.onSurface
)