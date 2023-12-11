package com.hedgdifuse.dishessampleapp.domain.impl

import com.hedgdifuse.dishessampleapp.data.DishRepository
import com.hedgdifuse.dishessampleapp.domain.DishesInteractor
import com.hedgdifuse.dishessampleapp.domain.DishesState
import com.hedgdifuse.dishessampleapp.domain.ErrorType
import com.hedgdifuse.dishessampleapp.domain.mapper.toDomainEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DishesInteractorImpl(
    private val coroutineScope: CoroutineScope,
    private val dishesRepository: DishRepository,
    private val hasNetwork: () -> Boolean
) : DishesInteractor {

    override val dishes = MutableStateFlow<DishesState>(DishesState.Loading)

    override suspend fun load() {
        dishes.value = DishesState.Loading
        if (hasNetwork()) {
            runCollector()
        } else {
            dishes.value = DishesState.Error(ErrorType.NO_NETWORK)
        }
    }

    override suspend fun deleteDishes(ids: List<String>) {
        dishesRepository.deleteDishes(ids)
    }

    override suspend fun getById(id: String) =
        dishesRepository.getDishById(id)?.toDomainEntity()

    private var collectorJob: Job? = null

    private fun runCollector() {
        collectorJob?.cancel()
        collectorJob = coroutineScope.launch {
            dishesRepository.dishes.collect { dishesList ->
                dishes.emit(DishesState.Loaded(dishesList.map { it.toDomainEntity() }))
            }
        }
    }
}
