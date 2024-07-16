package com.leotorrealba.desafiotecnico.data.repository

import com.leotorrealba.desafiotecnico.data.local.CartDao
import com.leotorrealba.desafiotecnico.data.local.entity.CartItemEntity
import com.leotorrealba.desafiotecnico.domain.model.CartItemData
import com.leotorrealba.desafiotecnico.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override suspend fun getCartItems(): Flow<List<CartItemData>> = cartDao.getAll()

    override suspend fun addToCart(cartItem: CartItemEntity) {
        val existingItem = cartDao.getItemById(cartItem.id)
        if (existingItem != null) {
            cartDao.updateQuantity(cartItem.id, existingItem.quantity + 1)
        } else {
            cartDao.insert(cartItem)
        }
    }

    override suspend fun removeFromCart(productId: Int) {
        cartDao.deleteCartItemByProductId(productId)
    }

    override suspend fun clearCart() {
        cartDao.deleteAll()
    }

    override suspend fun updateItemQuantity(productId: Int, quantity: Int) {
        cartDao.updateQuantity(productId, quantity)
    }

    override suspend fun getCartSize(): Int = cartDao.countItems()
}
