package com.abnamro.feature.repolist.ui

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
internal fun RepoTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
internal fun RepoOwnerName(
    ownerName: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = ownerName,
        maxLines = 1,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
internal fun RepoDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = description,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}