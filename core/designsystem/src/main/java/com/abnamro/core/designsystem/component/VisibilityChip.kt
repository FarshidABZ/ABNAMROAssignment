package com.abnamro.core.designsystem.component

import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VisibilityChip(text: String, modifier: Modifier) {
    Chip(
        modifier = modifier,
        onClick = { /* No Action */ },
        border = ChipDefaults.outlinedBorder,
        colors = ChipDefaults.outlinedChipColors(backgroundColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}