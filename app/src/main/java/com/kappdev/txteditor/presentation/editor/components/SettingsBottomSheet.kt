package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kappdev.txteditor.R
import com.kappdev.txteditor.data.Setting
import com.kappdev.txteditor.data.SettingsKey
import com.kappdev.txteditor.data.SettingsManager
import com.kappdev.txteditor.presentation.common.components.VerticalSpace
import com.kappdev.txteditor.presentation.editor.TextStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    settingsManager: SettingsManager,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val isThemeDark by settingsManager.getValueAsState(Setting.Theme)
    val textSize by settingsManager.getValueAsState(Setting.TextSize)
    val textStyle by settingsManager.getValueAsState(Setting.TextStyle)

    fun manageSettings(block: suspend SettingsManager.() -> Unit) {
        scope.launch { with(settingsManager) { block() } }
    }

    ModalBottomSheet(
        tonalElevation = 0.dp,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 54.dp)
        ) {
            Settings.Switch(
                title = stringResource(R.string.st_dark_mode),
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

            Settings.NumberPicker(
                title = stringResource(R.string.st_text_size),
                number = textSize,
                range = 8..24,
                onNumberChange = { newTextSize ->
                    manageSettings { setValueTo(SettingsKey.TEXT_SIZE, newTextSize) }
                }
            )

            VerticalSpace(16.dp)

            Settings.ItemsGroup {
                Settings.GroupItem(
                    title = stringResource(R.string.st_share),
                    icon = Icons.Rounded.Share,
                    onClick = { /* TODO */ }
                )
                Settings.GroupItem(
                    title = stringResource(R.string.st_copy),
                    icon = Icons.Rounded.ContentCopy,
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}