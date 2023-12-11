package com.hedgdifuse.dishessampleapp.data

import kotlinx.coroutines.flow.Flow

interface DishRepository {

    val dishes: Flow<List<DishDto>>

    suspend fun deleteDishes(ids: List<String>)

    suspend fun getDishById(id: String): DishDto?
}
