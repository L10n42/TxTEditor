package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.txteditor.R
import com.kappdev.txteditor.domain.model.EditorSettings
import com.kappdev.txteditor.presentation.common.KeyboardClosedEffect
import com.kappdev.txteditor.presentation.common.components.HorizontalSpace

@Composable
fun EditorField(
    value: TextFieldValue,
    settings: EditorSettings,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    scrollTo: (position: Float) -> Unit,
    onValueChange: (TextFieldValue) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var previousLine by remember { mutableIntStateOf(0) }
    var lineCount by remember { mutableIntStateOf(0) }

    KeyboardClosedEffect {
        focusManager.clearFocus()
    }

    val textStyle = LocalTextStyle.current.copy(
        fontSize = settings.textSize.sp,
        lineHeight = (settings.textSize * 1.25).sp,
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
            Row {
                AnimatedLineNumbering(isVisible = settings.showLineNumbering, lineCount = lineCount, style = textStyle)

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
            }
        },
        onTextLayout = { textLayoutResult ->
            lineCount = textLayoutResult.lineCount

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

@Composable
private fun AnimatedLineNumbering(
    isVisible: Boolean,
    lineCount: Int,
    style: TextStyle,
) {
    AnimatedContent(
        targetState = isVisible,
        label = "change line numbering visibility",
        transitionSpec = {
            expandHorizontally(
                expandFrom = Alignment.Start
            ) togetherWith shrinkHorizontally(
                shrinkTowards = Alignment.Start
            )
        }
    ) { numberingVisible ->
        when {
            numberingVisible -> LineNumbering(lineCount, style)
            else -> HorizontalSpace(16.dp)
        }
    }
}

@Composable
private fun LineNumbering(
    lineCount: Int,
    style: TextStyle
) {
    Text(
        text = getNumbersString(lineCount),
        style = style.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.End,
            fontFamily = FontFamily.Default
        ),
        modifier = Modifier.padding(start = 4.dp, end = 8.dp)
    )
}

private fun getNumbersString(number: Int): String {
    return buildString {
        for (i in 1..number) {
            append("$i.\n")
        }
    }
}