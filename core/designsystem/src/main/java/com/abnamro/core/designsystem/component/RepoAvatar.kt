package com.abnamro.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun RepoAvatar(imageUrl: String, modifier: Modifier) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = null,
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}