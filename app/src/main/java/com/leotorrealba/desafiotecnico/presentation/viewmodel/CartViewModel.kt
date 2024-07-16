package com.leotorrealba.desafiotecnico.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotorrealba.desafiotecnico.data.local.entity.CartItemEntity
import com.leotorrealba.desafiotecnico.domain.model.CartItemData
import com.leotorrealba.desafiotecnico.domain.model.Product
import com.leotorrealba.desafiotecnico.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val updateItemQuantityInCartUseCase: UpdateItemQuantityInCartUseCase
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemData>>(emptyList())
    val cartItems: StateFlow<List<CartItemData>> = _cartItems.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    private val _totalItemCount = MutableStateFlow(0)
    val totalItemCount: StateFlow<Int> = _totalItemCount.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collect { items ->
                _cartItems.value = items
                calculateTotalPrice(items)
                calculateTotalItemCount(items)
            }
        }
    }

    private fun calculateTotalPrice(items: List<CartItemData>) {
        _totalPrice.value = items.sumOf { it.price * it.quantity }
    }

    private fun calculateTotalItemCount(items: List<CartItemData>) {
        _totalItemCount.value = items.sumOf { it.quantity }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase(product)
        }
    }

    fun updateItemQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            if (quantity > 0) {
                updateItemQuantityInCartUseCase(productId, quantity)
            } else {
                removeFromCartUseCase(productId)
            }
            loadCartItems()
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            removeFromCartUseCase(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            clearCartUseCase()
        }
    }
}