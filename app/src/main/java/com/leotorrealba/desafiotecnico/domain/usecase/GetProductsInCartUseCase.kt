package com.leotorrealba.desafiotecnico.domain.usecase

import com.leotorrealba.desafiotecnico.domain.model.Product
import com.leotorrealba.desafiotecnico.domain.repository.CartRepository
import javax.inject.Inject

class GetProductsInCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(): Int {
        return cartRepository.getCartSize()
    }
}