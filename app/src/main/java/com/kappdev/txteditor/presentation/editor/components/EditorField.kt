package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.kappdev.txteditor.R
import com.kappdev.txteditor.domain.model.EditorSettings
import com.kappdev.txteditor.presentation.common.KeyboardClosedEffect

@Composable
fun EditorField(
    value: TextFieldValue,
    settings: EditorSettings,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    scrollTo: (position: Float) -> Unit,
    onValueChange: (TextFieldValue) -> Unit
) {
    var previousLine by remember { mutableIntStateOf(0) }
    val focusManager = LocalFocusManager.current

    KeyboardClosedEffect {
        focusManager.clearFocus()
    }

    val textStyle = LocalTextStyle.current.copy(
        fontSize = settings.textSize.sp,
        lineHeight = (settings.textSize + 3).sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = settings.textStyle.family,
        fontWeight = if (settings.isBold) FontWeight.SemiBold else FontWeight.Normal,
        fontStyle = if (settings.isItalic) FontStyle.Italic else FontStyle.Normal
    )

    BasicTextField(
        value = value,
        enabled = enable,
        modifier = modifier,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        cursorBrush = Brush.linearGradient(
            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary)
        ),
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Box {
                innerTextField()
                if (value.text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.enter_text_hint),
                        style = textStyle.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        },
        onTextLayout = { textLayoutResult ->
            val cursorPosition = value.selection.start
            val lastLine = (textLayoutResult.lineCount - 1)
            val currentLine = textLayoutResult.getLineForOffset(cursorPosition)

            if (previousLine != currentLine && currentLine == lastLine) {
                scrollTo(textLayoutResult.getLineBottom(currentLine))
                previousLine = currentLine
            }
        }
    )
}