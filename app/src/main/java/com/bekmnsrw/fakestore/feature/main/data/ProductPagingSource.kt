package com.bekmnsrw.fakestore.feature.main.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bekmnsrw.fakestore.feature.main.domain.ProductRepository
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import javax.inject.Inject

class ProductPagingSource @Inject constructor(
    private val productRepository: ProductRepository
) : PagingSource<Int, ProductMain>() {

    companion object {
        const val PAGE_SIZE = 15
        const val INITIAL_LOAD_SIZE = 0
    }

    override fun getRefreshKey(
        state: PagingState<Int, ProductMain>
    ): Int? = state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductMain> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset = if (params.key != null) position * PAGE_SIZE else INITIAL_LOAD_SIZE

        val response = productRepository.getProductsPaged(
            limit = params.loadSize,
            skip = offset
        )

        val nextKey = if (response.isEmpty()) null else position + (params.loadSize / PAGE_SIZE)

        return LoadResult.Page(
            data = response,
            prevKey = null,
            nextKey = nextKey
        )
    }
}
