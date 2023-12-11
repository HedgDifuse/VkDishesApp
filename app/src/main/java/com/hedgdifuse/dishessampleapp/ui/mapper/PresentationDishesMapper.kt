package com.hedgdifuse.dishessampleapp.ui.mapper

import com.hedgdifuse.dishessampleapp.R
import com.hedgdifuse.dishessampleapp.domain.DishesState
import com.hedgdifuse.dishessampleapp.ui.state.ButtonState
import com.hedgdifuse.dishessampleapp.ui.state.DishesScreenState

fun DishesState.toPresentationEntity() = when(this) {
    is DishesState.Error -> DishesScreenState.Error(R.string.no_internet_error)
    is DishesState.Loaded -> DishesScreenState.Loaded(dishes)
    DishesState.Loading -> DishesScreenState.Loading
}
