package com.bekmnsrw.fakestore.feature.main.data.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("limit") val limit: Int,
    @SerializedName("products") val products: List<Product>,
    @SerializedName("skip") val skip: Int,
    @SerializedName("total") val total: Int
)
