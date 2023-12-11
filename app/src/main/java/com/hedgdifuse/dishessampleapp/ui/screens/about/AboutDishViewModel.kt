package com.hedgdifuse.dishessampleapp.ui.screens.about

import androidx.lifecycle.ViewModel
import com.hedgdifuse.dishessampleapp.R
import com.hedgdifuse.dishessampleapp.domain.DishesInteractor
import com.hedgdifuse.dishessampleapp.ui.state.about.AboutDishScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class AboutDishViewModel @Inject constructor(
    private val dishesInteractor: DishesInteractor,
    coroutineScope: CoroutineScope
) : ViewModel() {

    private val dishIdFlow = MutableStateFlow<String?>(null)

    val screenState = dishIdFlow
        .filterNotNull()
        .transform {
            emit(AboutDishScreenState.Loading)
            emit(
                dishesInteractor.getById(it)
                    ?.let(AboutDishScreenState::Loaded)
                    ?: AboutDishScreenState.Error(R.string.no_dish_error)
            )
        }
        .stateIn(
            coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = AboutDishScreenState.Loading
        )

    fun load(id: String?) {
        dishIdFlow.value = id
    }
}
