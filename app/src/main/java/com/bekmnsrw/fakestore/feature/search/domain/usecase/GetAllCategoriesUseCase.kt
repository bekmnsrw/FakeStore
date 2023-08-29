package com.bekmnsrw.fakestore.feature.search.domain.usecase

import com.bekmnsrw.fakestore.feature.search.domain.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(): Flow<List<String>> = categoryRepository.getAllCategories()
}
