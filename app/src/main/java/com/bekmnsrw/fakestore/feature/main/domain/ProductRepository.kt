package com.bekmnsrw.fakestore.feature.main.domain

import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getAllProducts(): Flow<List<ProductMain>>
}
