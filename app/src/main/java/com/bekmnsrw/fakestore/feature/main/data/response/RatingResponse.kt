package com.bekmnsrw.fakestore.feature.main.data.response

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("rate") val rate: Double,
    @SerializedName("count") val count: Long
)
