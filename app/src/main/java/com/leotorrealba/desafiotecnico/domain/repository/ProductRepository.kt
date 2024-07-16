package com.leotorrealba.desafiotecnico.domain.repository

import com.leotorrealba.desafiotecnico.domain.model.Category
import com.leotorrealba.desafiotecnico.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<List<Product>>
    suspend fun getProductsByCategory(category: String): Flow<List<Product>>
    suspend fun getProductDetail(productId: Int): Product
    suspend fun getCategories(): List<String>
}