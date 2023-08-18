package com.bekmnsrw.fakestore.feature.main.data.mapper

import com.bekmnsrw.fakestore.feature.main.data.response.ProductResponse
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductDetails
import com.bekmnsrw.fakestore.feature.main.domain.dto.ProductMain

private const val ID_KEY = "id"
private const val TITLE_KEY = "title"
private const val DESCRIPTION_KEY = "description"
private const val CATEGORY_KEY = "category"
private const val IMAGE_KEY = "image"
private const val AVAILABLE_KEY = "available"
private const val ORDERS_KEY = "orders"
private const val FULL_PRICE_KEY = "fullPrice"
private const val DISCOUNT_PRICE_KEY = "discountPrice"
private const val RATE_KEY = "rate"
private const val COUNT_KEY = "count"

fun HashMap<*, *>.toProductResponse() = ProductResponse(
    id = this[ID_KEY].toString().toLong(),
    title = this[TITLE_KEY].toString(),
    fullPrice = this[FULL_PRICE_KEY].toString().toDouble(),
    discountPrice = this[DISCOUNT_PRICE_KEY].toString().toDouble(),
    description = this[DESCRIPTION_KEY].toString(),
    category = this[CATEGORY_KEY].toString(),
    image = this[IMAGE_KEY].toString(),
    rate = this[RATE_KEY].toString().toDouble(),
    count = this[COUNT_KEY].toString().toLong(),
    available = this[AVAILABLE_KEY].toString().toLong(),
    orders = this[ORDERS_KEY].toString().toLong()
)
fun ProductResponse.toProductMain(): ProductMain = ProductMain(
    id = id,
    title = title,
    fullPrice = fullPrice,
    discountPrice = discountPrice,
    image = image,
    rate = rate,
    count = count
)

fun List<ProductResponse>.toProductMainList(): List<ProductMain> = this.map { it.toProductMain() }

fun ProductResponse.toProductDetails(): ProductDetails = ProductDetails(
    id = id,
    title = title,
    fullPrice = fullPrice,
    discountPrice = discountPrice,
    description = description,
    category = category,
    image = image,
    rate = rate,
    count = count,
    available = available,
    orders = orders
)
