package com.leotorrealba.desafiotecnico.data.mapper

import com.leotorrealba.desafiotecnico.data.remote.dto.ProductDto
import com.leotorrealba.desafiotecnico.data.remote.dto.RatingDto
import com.leotorrealba.desafiotecnico.domain.model.Product
import com.leotorrealba.desafiotecnico.domain.model.Rating

object ProductMapper {

    fun fromDto(productDto: ProductDto): Product {
        return Product(
            id = productDto.id,
            title = productDto.title,
            price = productDto.price,
            description = productDto.description,
            category = productDto.category,
            image = productDto.image,
            rating = Rating(
                rate = productDto.rating.rate,
                count = productDto.rating.count
            )
        )
    }

    fun toDto(product: Product): ProductDto {
        return ProductDto(
            id = product.id,
            title = product.title,
            price = product.price,
            description = product.description,
            category = product.category,
            image = product.image,
            rating = RatingDto(
                rate = product.rating.rate,
                count = product.rating.count
            )
        )
    }
}