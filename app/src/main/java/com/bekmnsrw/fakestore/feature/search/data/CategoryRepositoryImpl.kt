package com.bekmnsrw.fakestore.feature.search.data

import com.bekmnsrw.fakestore.core.network.ProductApi
import com.bekmnsrw.fakestore.feature.main.data.mapper.toProductMainList
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
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

    override suspend fun getProductsOfCategory(
        category: String,
        limit: Int,
        skip: Int
    ): List<ProductMain> = productApi
        .getProductsOfCategory(
            category = category,
            limit = limit,
            skip = skip
        )
        .products
        .toProductMainList()
}
