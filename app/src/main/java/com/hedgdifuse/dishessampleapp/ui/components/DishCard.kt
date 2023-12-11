package com.hedgdifuse.dishessampleapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hedgdifuse.dishessampleapp.R
import com.hedgdifuse.dishessampleapp.domain.Dish

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DishCard(
    @PreviewParameter(provider = DishPreviewParameterProvider::class)
    dish: Dish,
    checked: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Card(onClick = onClick) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = dish.image,
                modifier = Modifier.width(100.dp).fillMaxHeight(),
                contentScale = ContentScale.Crop,
                contentDescription = null)

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f).padding(16.dp, 8.dp)
            ) {
                Text(dish.name, fontSize = 20.sp)

                Text(
                    stringResource(R.string.price_format).format(dish.price),
                    fontSize = 32.sp,
                    overflow = TextOverflow.Clip,
                    maxLines = 3,
                    fontWeight = FontWeight.Bold
                )
            }

            Checkbox(checked, onCheckedChange, modifier = Modifier.padding(end = 16.dp))
        }
    }
}
