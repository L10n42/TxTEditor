package com.kappdev.txteditor.editor_feature.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dialog {

    @Composable
    fun Text(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = modifier,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    @Composable
    fun Title(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = modifier,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    @Composable
    fun Button(
        title: String,
        enable: Boolean = true,
        onClick: () -> Unit
    ) {
        TextButton(
            enabled = enable,
            onClick = onClick
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    @Composable
    fun Platform(
        verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
        horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                content = content
            )
        }
    }
}