package com.abnamro.core.designsystem.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abnamro.core.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    tintColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    onNavigationClicked: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(16.dp),
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.background),
        navigationIcon = {
            if (onNavigationClicked != null) {
                Image(
                    modifier = Modifier
                        .clickable { onNavigationClicked() }
                        .size(40.dp)
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    colorFilter = ColorFilter.tint(tintColor),
                    contentDescription = ""
                )
            }
        }
    )
}