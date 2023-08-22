package com.bekmnsrw.fakestore.feature.main.data.mapper

import com.bekmnsrw.fakestore.feature.main.data.response.Product
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain

fun Product.toProductMain(): ProductMain = ProductMain(
    id = id,
    title = title,
    price = price,
    discountPercentage = discountPercentage,
    thumbnail = thumbnail,
    rating = rating,
    stock = stock
)

fun List<Product>.toProductMainList(): List<ProductMain> = this.map { it.toProductMain() }
