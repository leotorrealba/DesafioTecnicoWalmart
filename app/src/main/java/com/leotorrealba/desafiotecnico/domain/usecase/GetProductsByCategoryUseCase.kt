package com.leotorrealba.desafiotecnico.domain.usecase

import com.leotorrealba.desafiotecnico.domain.model.Product
import com.leotorrealba.desafiotecnico.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(category: String): Flow<List<Product>> {
        return productRepository.getProductsByCategory(category)
    }
}