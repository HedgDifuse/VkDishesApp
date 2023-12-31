package com.hedgdifuse.dishessampleapp.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hedgdifuse.dishessampleapp.domain.Dish
import com.hedgdifuse.dishessampleapp.domain.DishesInteractor
import com.hedgdifuse.dishessampleapp.ui.mapper.toPresentationEntity
import com.hedgdifuse.dishessampleapp.ui.state.list.ButtonState
import com.hedgdifuse.dishessampleapp.ui.state.list.DishesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DishesListViewModel @Inject constructor(
    private val dishesInteractor: DishesInteractor
) : ViewModel() {

    private val dishIdToRemove = MutableStateFlow(mutableSetOf<String>())
    private val removeButtonState = MutableStateFlow(ButtonState.DISABLED)
    private val mutex = Mutex()

    val buttonState: StateFlow<ButtonState> = removeButtonState
    val markedDishes: StateFlow<Set<String>> = dishIdToRemove
    val screenState = dishesInteractor.dishes
        .map { it.toPresentationEntity() }
        .onStart { dishesInteractor.load() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = DishesScreenState.Loading
        )

    suspend fun retry() = withContext(viewModelScope.coroutineContext) {
        if (screenState.value !is DishesScreenState.Error) return@withContext

        dishesInteractor.load()
    }

    suspend fun mark(dish: Dish, checked: Boolean) = withContext(viewModelScope.coroutineContext) {
        mutex.withLock {
            val set = dishIdToRemove.value.toMutableSet()

            if (!checked) {
                set -= dish.id
            } else {
                set += dish.id
            }

            removeButtonState.emit(if (set.isNotEmpty()) ButtonState.ACTIVE else ButtonState.DISABLED)
            dishIdToRemove.emit(set)
        }
    }

    suspend fun removeMarked() = withContext(viewModelScope.coroutineContext)  {
        val dishesIds = dishIdToRemove.replayCache.firstOrNull()?.toList() ?: return@withContext

        removeButtonState.emit(ButtonState.LOADING)

        dishesInteractor.deleteDishes(dishesIds)
        dishIdToRemove.emit(mutableSetOf())
        removeButtonState.emit(ButtonState.DISABLED)
    }
}
