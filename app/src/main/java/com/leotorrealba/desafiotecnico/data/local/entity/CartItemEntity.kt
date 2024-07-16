package com.leotorrealba.desafiotecnico.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    var quantity: Int = 1
)