package com.hedgdifuse.dishessampleapp.ui.components.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hedgdifuse.dishessampleapp.R

@Composable
fun ErrorScreen(
    onRetryClick: (() -> Unit)? = null,
    @StringRes errorText: Int,
    icon: ImageVector
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, contentDescription = null)
            Text(
                stringResource(errorText),
                textAlign = TextAlign.Center
            )
            onRetryClick?.let {
                Button(onClick = it) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
    }
}