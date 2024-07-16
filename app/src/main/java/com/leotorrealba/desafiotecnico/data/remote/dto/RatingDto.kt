package com.leotorrealba.desafiotecnico.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("rate")
    val rate: Double,

    @SerializedName("count")
    val count: Int
)