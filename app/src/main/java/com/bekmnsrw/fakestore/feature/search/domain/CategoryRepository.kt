package com.bekmnsrw.fakestore.feature.search.domain

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getAllCategories(): Flow<List<String>>
}
