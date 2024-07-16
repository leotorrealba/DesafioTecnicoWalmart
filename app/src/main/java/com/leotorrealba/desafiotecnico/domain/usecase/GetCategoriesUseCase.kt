package com.leotorrealba.desafiotecnico.domain.usecase

import com.leotorrealba.desafiotecnico.domain.repository.ProductRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): List<String> {
        return productRepository.getCategories()
    }
}