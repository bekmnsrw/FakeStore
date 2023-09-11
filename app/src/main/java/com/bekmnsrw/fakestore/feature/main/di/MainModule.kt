package com.bekmnsrw.fakestore.feature.main.di

import android.content.Context
import com.bekmnsrw.fakestore.core.network.ProductApi
import com.bekmnsrw.fakestore.feature.main.data.ProductRepositoryImpl
import com.bekmnsrw.fakestore.feature.main.data.ReminderRepositoryImpl
import com.bekmnsrw.fakestore.feature.main.domain.ProductRepository
import com.bekmnsrw.fakestore.feature.main.domain.ReminderRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module(includes = [MainBindModule::class])
@InstallIn(ActivityComponent::class)
class MainModule {

    @Provides
    fun provideProductRepository(
        productApi: ProductApi
    ): ProductRepository = ProductRepositoryImpl(productApi)

    @Provides
    fun provideReminderRepository(
        @ApplicationContext context: Context
    ): ReminderRepository = ReminderRepositoryImpl(context)
}

@Module
@InstallIn(SingletonComponent::class)
interface MainBindModule {

    @Binds
    fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    fun bindReminderRepository(
        reminderRepositoryImpl: ReminderRepositoryImpl
    ): ReminderRepository
}
