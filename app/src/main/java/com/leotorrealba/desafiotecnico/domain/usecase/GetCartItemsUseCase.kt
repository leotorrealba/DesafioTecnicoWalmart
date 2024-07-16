package com.leotorrealba.desafiotecnico.domain.usecase

import com.leotorrealba.desafiotecnico.domain.model.CartItemData
import com.leotorrealba.desafiotecnico.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(): Flow<List<CartItemData>> = cartRepository.getCartItems()
}