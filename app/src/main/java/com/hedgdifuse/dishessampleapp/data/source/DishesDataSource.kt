package com.hedgdifuse.dishessampleapp.data.source

import com.hedgdifuse.dishessampleapp.data.DishDto

interface DishesDataSource {

    suspend fun getById(id: String): DishDto?

    suspend fun getDishes(): List<DishDto>

    suspend fun remove(id: String)
}
