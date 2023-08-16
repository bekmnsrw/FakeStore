package com.bekmnsrw.fakestore.feature.main.data.mapper

import com.bekmnsrw.fakestore.feature.main.data.response.ProductResponse
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductDetails
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain

fun ProductResponse.toProductMain(): ProductMain = ProductMain(
    id = id,
    title = title,
    price = price,
    image = image,
    rating = rating.toRating()
)

fun List<ProductResponse>.toProductMainList(): List<ProductMain> = this.map { it.toProductMain() }

fun ProductResponse.toProductDetails(): ProductDetails = ProductDetails(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating.toRating()
)
