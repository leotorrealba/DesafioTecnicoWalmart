package com.leotorrealba.desafiotecnico.domain.model

data class CartItemData(
    val id: Int,
    val title: String,
    val price: Double,
    val quantity: Int = 1
)