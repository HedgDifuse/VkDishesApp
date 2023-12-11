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
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideNetworkDishDataSource(): DishesDataSource =
        NetworkDishesDataSourceImpl()

    @Singleton
    @Provides
    fun provideMemoryCacheDishDataSource(): MemoryCacheDishesDataSource =
        MemoryCacheDishesDataSourceImpl()

    @Singleton
    @Provides
    fun provideDishRepository(
        dishesDataSource: DishesDataSource,
        memoryCacheDishesDataSource: MemoryCacheDishesDataSource
    ): DishRepository = DishRepositoryImpl(
        dishesDataSource,
        memoryCacheDishesDataSource
    )
}
