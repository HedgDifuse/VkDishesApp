package com.hedgdifuse.dishessampleapp.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.hedgdifuse.dishessampleapp.R

@Composable
fun Price(
    price: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        stringResource(R.string.price_format).format(price),
        fontSize = 32.sp,
        overflow = TextOverflow.Clip,
        maxLines = 3,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
        textAlign = textAlign
    )
}