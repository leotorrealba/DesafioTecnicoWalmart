package com.leotorrealba.desafiotecnico.domain.usecase

import com.leotorrealba.desafiotecnico.domain.repository.CartRepository
import javax.inject.Inject

class UpdateItemQuantityInCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productId: Int, quantity: Int) {
        cartRepository.updateItemQuantity(productId, quantity)
    }
}