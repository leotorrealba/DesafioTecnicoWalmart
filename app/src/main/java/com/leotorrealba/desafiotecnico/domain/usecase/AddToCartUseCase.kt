package com.leotorrealba.desafiotecnico.domain.usecase

import com.leotorrealba.desafiotecnico.data.local.entity.CartItemEntity
import com.leotorrealba.desafiotecnico.domain.model.Product
import com.leotorrealba.desafiotecnico.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend operator fun invoke(product: Product) {
        cartRepository.addToCart(CartItemEntity(product.id, product.title, product.price))
    }
}