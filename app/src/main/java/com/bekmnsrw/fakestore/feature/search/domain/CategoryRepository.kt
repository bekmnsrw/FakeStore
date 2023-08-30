package com.bekmnsrw.fakestore.feature.search.domain

import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getAllCategories(): Flow<List<String>>
    suspend fun getProductsOfCategory(
        category: String,
        limit: Int,
        skip: Int
    ): List<ProductMain>
}
