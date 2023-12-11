package com.hedgdifuse.dishessampleapp.data.impl

import com.hedgdifuse.dishessampleapp.data.DishRepository
import com.hedgdifuse.dishessampleapp.data.source.DishesDataSource
import com.hedgdifuse.dishessampleapp.data.source.MemoryCacheDishesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class DishRepositoryImpl(
    private val networkDataSource: DishesDataSource,
    private val memoryCacheDataSource: MemoryCacheDishesDataSource
): DishRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val networkQueueScope = CoroutineScope(Dispatchers.IO.limitedParallelism(MAX_PARRALLEL_REQUESTS))

    override val dishes = memoryCacheDataSource.dishes
        .onStart {
            if (!memoryCacheDataSource.isSavedBefore) {
                memoryCacheDataSource.save(
                    withContext(networkQueueScope.coroutineContext) {
                        networkDataSource.getDishes()
                    }
                )
            }
        }

    override suspend fun deleteDishes(ids: List<String>) {
        awaitAll(
            *ids
                .map { id -> networkQueueScope.async { networkDataSource.remove(id) } }
                .toTypedArray()
        )

        ids.forEach { memoryCacheDataSource.remove(it) }
    }

    override suspend fun getDishById(id: String) =
        memoryCacheDataSource.getById(id)

    companion object {
        private const val MAX_PARRALLEL_REQUESTS = 4
    }
}
