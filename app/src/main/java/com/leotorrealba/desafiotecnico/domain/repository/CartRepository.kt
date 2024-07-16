package com.leotorrealba.desafiotecnico.domain.repository

import com.leotorrealba.desafiotecnico.data.local.entity.CartItemEntity
import com.leotorrealba.desafiotecnico.domain.model.CartItemData
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCartItems(): Flow<List<CartItemData>>
    suspend fun addToCart(cartItemData: CartItemEntity)
    suspend fun removeFromCart(productId: Int)
    suspend fun clearCart()
    suspend fun updateItemQuantity(productId: Int, quantity: Int)
    suspend fun getCartSize(): Int
}