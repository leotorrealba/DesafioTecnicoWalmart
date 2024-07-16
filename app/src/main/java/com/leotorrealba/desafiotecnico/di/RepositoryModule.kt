package com.leotorrealba.desafiotecnico.di

import com.leotorrealba.desafiotecnico.data.repository.ProductRepositoryImpl
import com.leotorrealba.desafiotecnico.data.repository.CartRepositoryImpl
import com.leotorrealba.desafiotecnico.domain.repository.ProductRepository
import com.leotorrealba.desafiotecnico.domain.repository.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository
}