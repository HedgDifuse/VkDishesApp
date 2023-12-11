package com.hedgdifuse.dishessampleapp.domain

sealed interface DishesState {
    data object Loading : DishesState
    class Loaded(val dishes: List<Dish>) : DishesState
    class Error(val errorType: ErrorType) : DishesState
}
