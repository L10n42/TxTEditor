package com.kappdev.txteditor.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable

@Composable
fun defaultSwitchColors() = SwitchDefaults.colors(
    checkedThumbColor = MaterialTheme.colorScheme.surface,
    checkedTrackColor = MaterialTheme.colorScheme.primary,
    checkedBorderColor = MaterialTheme.colorScheme.primary,
    checkedIconColor = MaterialTheme.colorScheme.primary,

    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
    uncheckedTrackColor = MaterialTheme.colorScheme.surface,
    uncheckedBorderColor = MaterialTheme.colorScheme.onSurface,
    uncheckedIconColor = MaterialTheme.colorScheme.surface,

    disabledCheckedThumbColor =  MaterialTheme.colorScheme.surface,
    disabledCheckedTrackColor = MaterialTheme.colorScheme.primary,
    disabledCheckedBorderColor = MaterialTheme.colorScheme.primary,
    disabledCheckedIconColor = MaterialTheme.colorScheme.primary,

    disabledUncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
    disabledUncheckedTrackColor = MaterialTheme.colorScheme.surface,
    disabledUncheckedBorderColor = MaterialTheme.colorScheme.onSurface,
    disabledUncheckedIconColor = MaterialTheme.colorScheme.surface
)