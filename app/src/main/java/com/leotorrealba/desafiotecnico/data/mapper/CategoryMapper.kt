package com.leotorrealba.desafiotecnico.data.mapper

import com.leotorrealba.desafiotecnico.data.remote.dto.CategoryDto
import com.leotorrealba.desafiotecnico.domain.model.Category

object CategoryMapper {

    fun fromDto(categoryDto: CategoryDto): Category {
        return Category(
            id = categoryDto.id,
            name = categoryDto.name,
            image = categoryDto.image
        )
    }

    fun toDto(category: Category): CategoryDto {
        return CategoryDto(
            id = category.id,
            name = category.name,
            image = category.image
        )
    }
}