package com.bekmnsrw.fakestore.feature.main.data.mapper

import com.bekmnsrw.fakestore.feature.main.data.response.Product
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductDetails
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain
import kotlin.math.roundToInt

fun Product.toProductMain(): ProductMain = ProductMain(
    id = id,
    title = title,
    price = price,
    discountPrice = ((price * (1 - discountPercentage / 100)) * 10.0).roundToInt() / 10.0,
    thumbnail = "https://image.kazanexpress.ru/cfcg9dodrnmhibl4f10g/original.jpg",
    rating = rating,
    stock = stock
)

fun List<Product>.toProductMainList(): List<ProductMain> = this.map { it.toProductMain() }

fun Product.toProductDetails(): ProductDetails = ProductDetails(
    id = id,
    title = title,
    description = description,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    brand = brand,
    category = category,
    thumbnail = thumbnail,
    images = images
)
