
package com.abnamro.core.designsystem.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler

@Composable
fun WebButton(modifier: Modifier = Modifier, externalURL: String, text: String) {
    val uriHandler = LocalUriHandler.current
    Button(modifier = modifier, onClick = {
        uriHandler.openUri(externalURL)
    }) {
        Text(text = text)
    }
}