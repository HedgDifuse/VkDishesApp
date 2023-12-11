package com.hedgdifuse.dishessampleapp.domain.di

import com.hedgdifuse.dishessampleapp.common.di.CommonModule
import com.hedgdifuse.dishessampleapp.data.DishRepository
import com.hedgdifuse.dishessampleapp.data.di.DataModule
import com.hedgdifuse.dishessampleapp.domain.DishesInteractor
import com.hedgdifuse.dishessampleapp.domain.impl.DishesInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module(includes = [DataModule::class])
class DomainModule {

    @Provides
    fun provideDishesInteractor(
        coroutineScope: CoroutineScope,
        dishesRepository: DishRepository,
        @Named(CommonModule.HAS_NETWORK_INJECT_NAME) hasNetwork: () -> Boolean
    ): DishesInteractor = DishesInteractorImpl(
        coroutineScope,
        dishesRepository,
        hasNetwork
    )
}
