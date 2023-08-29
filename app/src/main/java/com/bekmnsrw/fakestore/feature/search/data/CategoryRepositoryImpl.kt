package com.bekmnsrw.fakestore.feature.search.data

import com.bekmnsrw.fakestore.core.network.ProductApi
import com.bekmnsrw.fakestore.feature.search.domain.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
) : CategoryRepository {

    override suspend fun getAllCategories(): Flow<List<String>> = flow {
        emit(
            productApi.getAllCategories()
        )
    }
}
