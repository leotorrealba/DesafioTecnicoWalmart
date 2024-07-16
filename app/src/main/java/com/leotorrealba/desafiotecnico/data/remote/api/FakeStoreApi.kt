package com.leotorrealba.desafiotecnico.data.remote.api

import com.leotorrealba.desafiotecnico.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FakeStoreApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): List<ProductDto>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(
        @Path("categoryName") categoryName: String,
        @Query("limit") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): List<ProductDto>
}