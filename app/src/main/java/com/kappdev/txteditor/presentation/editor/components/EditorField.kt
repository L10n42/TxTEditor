package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.sp
import com.kappdev.txteditor.R
import com.kappdev.txteditor.domain.model.EditorSettings
import com.kappdev.txteditor.presentation.common.KeyboardClosedEffect

@Composable
fun EditorField(
    value: String,
    settings: EditorSettings,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    scrollTo: (position: Float) -> Unit,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var previousLineCount = remember { 0 }

    KeyboardClosedEffect {
        focusManager.clearFocus()
    }

    val textStyle = LocalTextStyle.current.copy(
        fontSize = settings.textSize.sp,
        lineHeight = (settings.textSize + 3).sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = settings.textStyle.family
    )

    BasicTextField(
        value = value,
        enabled = enable,
        modifier = modifier,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        cursorBrush = Brush.linearGradient(
            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary)
        ),
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Box {
                innerTextField()
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.enter_text_hint),
                        style = textStyle.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(0.32f)
                        )
                    )
                }
            }
        },
        onTextLayout = { textLayoutResult ->
            val lineCount = textLayoutResult.lineCount
            val currentTextEnd = value.lastOrNull()?.toString() ?: ""

            if (lineCount != previousLineCount) {
                val lastLine = (lineCount - 1)
                if (currentTextEnd == "\n") {
                    scrollTo(textLayoutResult.getLineBottom(lastLine))
                }
                previousLineCount = lineCount
            }
        }
    )
}