package com.leotorrealba.desafiotecnico.domain.usecase

import com.leotorrealba.desafiotecnico.domain.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke() {
        cartRepository.clearCart()
    }
}