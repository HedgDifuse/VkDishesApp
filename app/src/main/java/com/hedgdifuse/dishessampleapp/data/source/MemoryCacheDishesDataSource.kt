package com.hedgdifuse.dishessampleapp.data.source

import com.hedgdifuse.dishessampleapp.data.DishDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MemoryCacheDishesDataSource : DishesDataSource {

    val dishes: Flow<List<DishDto>>

    val isSavedBefore: Boolean

    suspend fun save(dishes: List<DishDto>)
}
