package com.hedgdifuse.dishessampleapp.data.di

import com.hedgdifuse.dishessampleapp.data.DishRepository
import com.hedgdifuse.dishessampleapp.data.impl.DishRepositoryImpl
import com.hedgdifuse.dishessampleapp.data.source.DishesDataSource
import com.hedgdifuse.dishessampleapp.data.source.MemoryCacheDishesDataSource
import com.hedgdifuse.dishessampleapp.data.source.impl.MemoryCacheDishesDataSourceImpl
import com.hedgdifuse.dishessampleapp.data.source.impl.NetworkDishesDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    fun provideNetworkDishDataSource(): DishesDataSource =
        NetworkDishesDataSourceImpl()

    @Provides
    fun provideMemoryCacheDishDataSource(): MemoryCacheDishesDataSource =
        MemoryCacheDishesDataSourceImpl()

    @Provides
    fun provideDishRepository(
        dishesDataSource: DishesDataSource,
        memoryCacheDishesDataSource: MemoryCacheDishesDataSource
    ): DishRepository = DishRepositoryImpl(
        dishesDataSource,
        memoryCacheDishesDataSource
    )
}
