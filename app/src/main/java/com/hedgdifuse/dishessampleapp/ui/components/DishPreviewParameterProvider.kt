package com.hedgdifuse.dishessampleapp.ui.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hedgdifuse.dishessampleapp.domain.Dish

class DishPreviewParameterProvider : PreviewParameterProvider<Dish> {

    override val values = sequenceOf(
        Dish(
            "5ed8da011f071c00465b2028",
            "Бургер \"Русский\" Бургер \"Русский\" Бургер \"Русский\" Бургер \"Русский\"",
            "460 г • Две котлеты из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, фирменный соус, лист салата, томат, маринованный лук, маринованные огурчики, двойной сыр чеддер, соус ранч.",
            "https://loremflickr.com/320/240/food?3",
            "379"
        )
    )
}
