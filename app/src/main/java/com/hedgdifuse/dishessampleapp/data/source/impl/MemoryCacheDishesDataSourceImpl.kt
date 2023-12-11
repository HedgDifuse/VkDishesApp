package com.hedgdifuse.dishessampleapp.data.source.impl

import com.hedgdifuse.dishessampleapp.data.DishDto
import com.hedgdifuse.dishessampleapp.data.source.MemoryCacheDishesDataSource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MemoryCacheDishesDataSourceImpl : MemoryCacheDishesDataSource {

    private val mutex = Mutex()

    override val dishes = MutableSharedFlow<List<DishDto>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @Volatile
    override var isSavedBefore: Boolean = false

    override suspend fun getById(id: String) = dishes.replayCache.firstOrNull()?.firstOrNull { it.id == id }

    override suspend fun save(dishes: List<DishDto>) {
        this.dishes.emit(dishes)
        isSavedBefore = true
    }

    override suspend fun getDishes() = dishes.replayCache.firstOrNull().orEmpty()

    override suspend fun remove(id: String) {
        val dishesList = dishes.replayCache.firstOrNull() ?: return

        mutex.withLock {
            dishes.emit(dishesList.filterNot { it.id == id })
        }
    }
}
