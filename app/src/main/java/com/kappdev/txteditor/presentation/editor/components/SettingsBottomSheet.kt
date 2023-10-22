package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Numbers
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kappdev.txteditor.R
import com.kappdev.txteditor.data.Setting
import com.kappdev.txteditor.data.SettingsKey
import com.kappdev.txteditor.data.SettingsManager
import com.kappdev.txteditor.presentation.common.components.VerticalSpace
import com.kappdev.txteditor.presentation.editor.EditorViewModel
import com.kappdev.txteditor.presentation.editor.TextStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    settingsManager: SettingsManager,
    viewModel: EditorViewModel,
    onDismiss: () -> Unit
) {
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val wordCount = remember { viewModel.getWordCount() }
    val filename = remember { viewModel.getCurrentFilename() }

    val isThemeDark by settingsManager.getValueAsState(Setting.Theme)
    val textSize by settingsManager.getValueAsState(Setting.TextSize)
    val textStyle by settingsManager.getValueAsState(Setting.TextStyle)
    val isTextBold by settingsManager.getValueAsState(Setting.IsTextBold)
    val isTextItalic by settingsManager.getValueAsState(Setting.IsTextItalic)
    val showLineNumbering by settingsManager.getValueAsState(Setting.ShowLineNumbering)

    fun manageSettings(block: suspend SettingsManager.() -> Unit) {
        scope.launch { with(settingsManager) { block() } }
    }

    ModalBottomSheet(
        sheetState = state,
        tonalElevation = 0.dp,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
        ) {
            Settings.Switch(
                title = stringResource(R.string.st_dark_mode),
                icon = Icons.Rounded.Palette,
                isChecked = isThemeDark,
                onCheckedChange = { isDark ->
                    manageSettings { setValueTo(SettingsKey.THEME, isDark) }
                }
            )

            VerticalSpace(16.dp)

            Settings.TextStylePicker(
                currentStyle = TextStyle.valueOf(textStyle),
                onStyleChange = { newTextStyle ->
                    manageSettings { setValueTo(SettingsKey.TEXT_STYLE, newTextStyle.name) }
                }
            )

            VerticalSpace(16.dp)

            Settings.ItemsGroup {
                Settings.NumberPicker(
                    title = stringResource(R.string.st_text_size),
                    icon = Icons.Rounded.FormatSize,
                    number = textSize,
                    hasBorder = false,
                    range = 8..24,
                    onNumberChange = { newTextSize ->
                        manageSettings { setValueTo(SettingsKey.TEXT_SIZE, newTextSize) }
                    }
                )
                Settings.Switch(
                    title = stringResource(R.string.st_bold),
                    icon = Icons.Rounded.FormatBold,
                    isChecked = isTextBold,
                    hasBorder = false,
                    onCheckedChange = { isBold ->
                        manageSettings { setValueTo(SettingsKey.IS_TEXT_BOLD, isBold) }
                    }
                )
                Settings.Switch(
                    title = stringResource(R.string.st_italic),
                    icon = Icons.Rounded.FormatItalic,
                    isChecked = isTextItalic,
                    hasBorder = false,
                    onCheckedChange = { isItalic ->
                        manageSettings { setValueTo(SettingsKey.IS_TEXT_ITALIC, isItalic) }
                    }
                )
                Settings.Switch(
                    title = stringResource(R.string.st_line_numbering),
                    icon = Icons.Rounded.Numbers,
                    isChecked = showLineNumbering,
                    hasBorder = false,
                    onCheckedChange = { show ->
                        manageSettings { setValueTo(SettingsKey.SHOW_LINE_NUMBERING, show) }
                    }
                )
            }

            VerticalSpace(16.dp)

            Settings.ItemsGroup {
                Settings.GroupItem(
                    title = stringResource(R.string.st_share),
                    icon = Icons.Rounded.Share,
                    onClick = {
                        viewModel.share()
                        onDismiss()
                    }
                )
                Settings.GroupItem(
                    title = stringResource(R.string.st_copy),
                    icon = Icons.Rounded.ContentCopy,
                    onClick = {
                        viewModel.copyToClipboard()
                        onDismiss()
                    }
                )
            }

            VerticalSpace(16.dp)

            Settings.ItemsGroup {
                Settings.TextItem(
                    text = stringResource(R.string.st_file, filename),
                    hasBorder = false
                )
                Settings.TextItem(
                    text = stringResource(R.string.st_word_count, wordCount),
                    hasBorder = false
                )
            }
        }
    }
}
