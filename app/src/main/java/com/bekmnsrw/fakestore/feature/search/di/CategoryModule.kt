package com.bekmnsrw.fakestore.feature.search.di

import com.bekmnsrw.fakestore.core.network.ProductApi
import com.bekmnsrw.fakestore.feature.search.data.CategoryRepositoryImpl
import com.bekmnsrw.fakestore.feature.search.domain.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module(includes = [SearchBindModule::class])
@InstallIn(ActivityComponent::class)
class SearchModule {

    @Provides
    fun provideCategoryRepository(
        productApi: ProductApi
    ): CategoryRepository = CategoryRepositoryImpl(productApi)
}

@Module
@InstallIn(SingletonComponent::class)
interface SearchBindModule {

    @Binds
    fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository
}
