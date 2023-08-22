package com.bekmnsrw.fakestore.feature.main.di

import com.bekmnsrw.fakestore.core.network.ProductApi
import com.bekmnsrw.fakestore.feature.main.data.ProductRepositoryImpl
import com.bekmnsrw.fakestore.feature.main.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module(includes = [MainBindModule::class])
@InstallIn(ActivityComponent::class)
class MainModule {

    @Provides
    fun provideProductRepository(
        productApi: ProductApi
    ): ProductRepository = ProductRepositoryImpl(productApi)
}

@Module
@InstallIn(SingletonComponent::class)
interface MainBindModule {

    @Binds
    fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
