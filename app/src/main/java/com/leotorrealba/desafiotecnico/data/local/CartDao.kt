package com.leotorrealba.desafiotecnico.data.local

import androidx.room.*
import com.leotorrealba.desafiotecnico.data.local.entity.CartItemEntity
import com.leotorrealba.desafiotecnico.domain.model.CartItemData
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAll(): Flow<List<CartItemData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItemData: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteCartItemByProductId(id: Int)

    @Query("DELETE FROM cart_items")
    suspend fun deleteAll()

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: Int, quantity: Int)

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun countItems(): Int

    @Query("SELECT * FROM cart_items WHERE id = :id")
    suspend fun getItemById(id: Int): CartItemData?
}