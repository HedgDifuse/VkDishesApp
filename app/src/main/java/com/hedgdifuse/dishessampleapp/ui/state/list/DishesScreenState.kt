package com.hedgdifuse.dishessampleapp.ui.state.list

import androidx.annotation.StringRes
import com.hedgdifuse.dishessampleapp.domain.Dish

sealed interface DishesScreenState {

    data class Loaded(
        val dishes: List<Dish>
    ) : DishesScreenState

    data object Loading : DishesScreenState

    data class Error(
        @StringRes val errorText: Int
    ) : DishesScreenState
}
