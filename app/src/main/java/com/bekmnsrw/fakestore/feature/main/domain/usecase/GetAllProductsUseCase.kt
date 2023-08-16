package com.bekmnsrw.fakestore.feature.main.domain.usecase

import com.bekmnsrw.fakestore.feature.main.domain.ProductRepository
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend operator fun invoke(): Flow<List<ProductMain>> = productRepository.getAllProducts()
}
