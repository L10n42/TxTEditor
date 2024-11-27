package com.kappdev.txteditor.editor_feature.presentation.editor.components

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kappdev.txteditor.R
import com.kappdev.txteditor.editor_feature.presentation.common.components.HorizontalSpace
import com.kappdev.txteditor.editor_feature.presentation.editor.HistoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    viewModel: HistoryViewModel = hiltViewModel(),
    openFile: (file: Uri) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val history = viewModel.history.value

    LaunchedEffect(Unit) {
        viewModel.getHistory()
    }

    ModalBottomSheet(
        sheetState = state,
        tonalElevation = 0.dp,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemIf(history.isEmpty()) {
                EmptyHistory()
            }

            items(history.reversed(), { it.hashCode() }) { item ->
                val filename = remember { viewModel.getFilenameOf(item) }

                AnimatedHistoryCard(
                    filename = filename,
                    onRemove = {
                        viewModel.removeFromHistory(item)
                    },
                    onClick = {
                        openFile(item)
                        onDismiss()
                    }
                )
            }

            itemIf(history.isNotEmpty()) {
                ClearHistoryButton {
                    viewModel.clearHistory()
                    onDismiss()
                }
            }
        }
    }
}

fun LazyListScope.itemIf(condition: Boolean, content: @Composable LazyItemScope.() -> Unit) {
    if (condition) {
        item { content() }
    }
}

@Composable
private fun EmptyHistory() {
    Text(
        text = stringResource(R.string.empty_history_msg),
        fontSize = 16.sp,
        modifier = Modifier.padding(start = 16.dp),
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun ClearHistoryButton(
    modifier: Modifier = Modifier,
    clearHistory: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            modifier = modifier,
            onClick = clearHistory
        ) {
            Text(
                text = stringResource(R.string.clear_history),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun AnimatedHistoryCard(
    filename: String,
    onRemove: () -> Unit,
    onClick: () -> Unit,
    duration: Int = 350
) {
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }

    fun animatedRemove() {
        scope.launch {
            isVisible = false
            delay(duration.toLong())
            onRemove()
        }
    }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(duration)
        ) + scaleIn(
            initialScale = 0.6f,
            animationSpec = tween(duration)
        ),
        exit = scaleOut(
            targetScale = 0.6f,
            animationSpec = tween(duration)
        ) + fadeOut(
            animationSpec = tween(duration)
        ),
    ) {
        HistoryCard(
            filename = filename,
            onClick = onClick,
            onRemove = ::animatedRemove
        )
    }
}

@Composable
private fun HistoryCard(
    filename: String,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        tonalElevation = 0.dp,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.background,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HorizontalSpace(16.dp)
            Text(
                text = filename,
                maxLines = 1,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground
            )

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "remove file from history"
                )
            }
        }
    }
}