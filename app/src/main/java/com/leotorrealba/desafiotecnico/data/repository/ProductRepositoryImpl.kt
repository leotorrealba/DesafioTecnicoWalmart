package com.leotorrealba.desafiotecnico.data.repository

import com.leotorrealba.desafiotecnico.data.remote.api.FakeStoreApi
import com.leotorrealba.desafiotecnico.data.remote.dto.ProductDto
import com.leotorrealba.desafiotecnico.domain.model.Product
import com.leotorrealba.desafiotecnico.domain.model.Rating
import com.leotorrealba.desafiotecnico.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: FakeStoreApi
) : ProductRepository {

    override suspend fun getProducts(): Flow<List<Product>> = flow {
        try {
            val products = api.getAllProducts().map { it.toDomainModel() }
            emit(products)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getProductsByCategory(category: String): Flow<List<Product>> = flow {
        try {
            val products = api.getProductsByCategory(category).map { it.toDomainModel() }
            emit(products)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getProductDetail(productId: Int): Product {
        return api.getProductById(productId).toDomainModel()
    }

    override suspend fun getCategories(): List<String> {
        return api.getCategories()
    }

    private fun ProductDto.toDomainModel(): Product {
        return Product(
            id = this.id,
            title = this.title,
            price = this.price,
            description = this.description,
            category = this.category,
            image = this.image,
            rating = Rating(
                rate = this.rating.rate,
                count = this.rating.count
            )
        )
    }
}