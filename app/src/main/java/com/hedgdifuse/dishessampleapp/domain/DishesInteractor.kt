package com.hedgdifuse.dishessampleapp.domain

import kotlinx.coroutines.flow.StateFlow

interface DishesInteractor {

   val dishes: StateFlow<DishesState>

   suspend fun load()

   suspend fun deleteDishes(ids: List<String>)

   suspend fun getById(id: String): Dish?
}
