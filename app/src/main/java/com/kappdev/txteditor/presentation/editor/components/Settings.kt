package com.kappdev.txteditor.presentation.editor.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.txteditor.presentation.common.components.HorizontalSpace
import com.kappdev.txteditor.presentation.common.components.PopupNumberPicker
import com.kappdev.txteditor.presentation.common.defaultSwitchColors

object Settings

@Composable
fun Settings.NumberPicker(
    title: String,
    number: Int,
    range: Iterable<Int>,
    border: Boolean = true,
    icon: ImageVector? = null,
    onNumberChange: (newNumber: Int) -> Unit
) {
    var isPickerVisible by remember { mutableStateOf(false) }
    Form(border = border) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let {
                Icon(icon = icon)
                HorizontalSpace(8.dp)
            }
            Title(text = title)
        }
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(26.dp)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(4.dp)
                )
                .clip(RoundedCornerShape(4.dp))
                .clickable { isPickerVisible = true },
            contentAlignment = Alignment.Center
        ) {
            Title(text = number.toString())
            PopupNumberPicker(
                visible = isPickerVisible,
                range = range,
                initialValue = number,
                onDismiss = { newValue ->
                    onNumberChange(newValue)
                    isPickerVisible = false
                }
            )
        }
    }
}

@Composable
fun Settings.ItemsGroup(
    modifier: Modifier = Modifier,
    divider: @Composable () -> Unit = { DefaultDivider() },
    content: @Composable () -> Unit
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        val contentPlaceables = subcompose("content", content).map { it.measure(constraints) }

        val itemsHeight = contentPlaceables.sumOf { it.height }
        val dividerHeight = subcompose("pre-calculate-divider", divider).map { it.measure(constraints) }.sumOf { it.height }
        val dividersHeight = dividerHeight * (contentPlaceables.size + 1)

        layout(
            width = constraints.maxWidth,
            height = (itemsHeight + dividersHeight)
        ) {
            var yPosition = 0

            fun updateYBy(value: Int) { yPosition += value }

            subcompose("pre-divider", divider).forEach {
                val placeable = it.measure(constraints)
                placeable.place(0, yPosition)
                updateYBy(placeable.height)
            }

            contentPlaceables.forEachIndexed { index, placeable ->
                placeable.place(x = 0, y = yPosition)
                updateYBy(placeable.height)

                subcompose("divider-$index", divider).forEach {
                    val dividerPlaceable = it.measure(constraints)
                    dividerPlaceable.place(0, yPosition)
                    updateYBy(dividerPlaceable.height)
                }
            }
        }
    }
}

@Composable
fun Settings.GroupItem(
    title: String,
    icon: ImageVector? = null,
    onClick: () -> Unit
) = Item(title = title, border = false, icon = icon, onClick =  onClick)

@Composable
fun Settings.Item(
    title: String,
    border: Boolean = true,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    Form(
        border = border,
        contentArrangement = Arrangement.Start,
        onClick = onClick
    ) {
        icon?.let {
            Icon(icon = icon)
            HorizontalSpace(8.dp)
        }
        Title(text = title)
    }
}

@Composable
fun Settings.Switch(
    title: String,
    isChecked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Form {
        Title(text = title)
        Switch(
            checked = isChecked,
            colors = defaultSwitchColors(),
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            thumbContent = {
                AnimatedContent(
                    targetState = isChecked,
                    transitionSpec = {
                        scaleIn() togetherWith scaleOut()
                    },
                    label = "animated switch icon"
                ) { checked ->
                    val icon = if (checked) Icons.Rounded.Done else Icons.Rounded.Close
                    Icon(
                        imageVector = icon,
                        modifier = Modifier.size(20.dp),
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@Composable
fun Settings.Icon(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icon,
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.size(20.dp),
        contentDescription = null
    )
}

@Composable
fun Settings.Title(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        maxLines = 1,
        fontSize = 16.sp,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun Settings.Form(
    border: Boolean = true,
    height: Dp = DefaultItemHeight,
    contentArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    val clickModifier = onClick?.let { Modifier.clickable(onClick = onClick) } ?: Modifier
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background.copy(0.16f))
    ) {
        if (border) DefaultDivider()
        Row(
            modifier = Modifier
                .height(height)
                .fillMaxWidth()
                .then(clickModifier)
                .padding(horizontal = DefaultItemPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = contentArrangement,
            content = content
        )
        if (border) DefaultDivider()
    }
}

@Composable
fun Settings.DefaultDivider() {
    Divider(thickness = 0.6.dp)
}

private val DefaultItemHeight = 40.dp
private val DefaultItemPadding = 16.dp