package com.hedgdifuse.dishessampleapp.ui.state.about

import androidx.annotation.StringRes
import com.hedgdifuse.dishessampleapp.domain.Dish

sealed interface AboutDishScreenState {

    data object Loading : AboutDishScreenState

    data class Loaded(val dish: Dish) : AboutDishScreenState

    data class Error(@StringRes val errorText: Int) : AboutDishScreenState
}