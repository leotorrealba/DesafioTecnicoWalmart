package com.leotorrealba.desafiotecnico.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotorrealba.desafiotecnico.domain.model.Product
import com.leotorrealba.desafiotecnico.domain.usecase.GetCategoriesUseCase
import com.leotorrealba.desafiotecnico.domain.usecase.GetProductDetailUseCase
import com.leotorrealba.desafiotecnico.domain.usecase.GetProductsByCategoryUseCase
import com.leotorrealba.desafiotecnico.domain.usecase.GetProductsUseCase
import com.leotorrealba.desafiotecnico.utilities.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _selectedProducts = MutableStateFlow<List<Product>>(emptyList())
    val selectedProducts: StateFlow<List<Product>> = _selectedProducts.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _featuredProduct = MutableStateFlow<Product?>(null)
    val featuredProduct: StateFlow<Product?> = _featuredProduct

    private val _currentProduct = MutableStateFlow<Product?>(null)
    val currentProduct: StateFlow<Product?> = _currentProduct

    init {
        fetch()
    }

    fun fetch() {
        fetchProducts()
    }

    fun fetchCategories() {
        if (_uiState.value == UiState.Loading) return
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val categoriesList = getCategoriesUseCase()
                _categories.value = categoriesList
                _uiState.value = UiState.Success
                if (products.value.isEmpty()) {
                    fetchProducts()
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Imposible cargar las categorias")
            }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getProductsUseCase()
                .onEach { products ->
                    _products.value = products
                    calculateFeaturedProduct(products)
                    _uiState.value = UiState.Success
                    if (categories.value.isEmpty()) {
                        fetchCategories()
                    }
                }
                .catch { _ ->
                    _uiState.value = UiState.Error("Imposible cargar los productos")
                }
                .launchIn(this)
        }
    }

    fun fetchProductsByCategory(category: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getProductsByCategoryUseCase(category)
                .onEach { products ->
                    _products.value = products
                    calculateFeaturedProduct(products)
                    _uiState.value = UiState.Success
                }
                .catch { _ ->
                    _uiState.value = UiState.Error("Imposible cargar los productos de la categoria $category")
                }
                .launchIn(this)
        }
    }

    fun loadProductById(productId: Int) {
        viewModelScope.launch {
            val product = getProductDetailUseCase(productId)
            _currentProduct.value = product
        }
    }

    fun addProductToList(product: Product) {
        val currentList = _selectedProducts.value.toMutableList()
        if (!currentList.any { it.id == product.id }) {
            currentList.add(product)
            _selectedProducts.value = currentList
        }
    }

    fun removeProductFromList(productId: Int) {
        val currentList = _selectedProducts.value.toMutableList()
        val productToRemove = currentList.find { it.id == productId }
        if (productToRemove != null) {
            currentList.remove(productToRemove)
            _selectedProducts.value = currentList
        }
    }

    private fun calculateFeaturedProduct(productList: List<Product>) {
        val featured = productList.maxByOrNull { it.rating.rate * it.rating.count }
        _featuredProduct.value = featured
    }

    fun loadAgain(category: String? = null) {
        if (category != null) {
            fetchProductsByCategory(category)
        } else {
            fetch()
        }
    }

    fun getProductById(id: Int): Product? {
        return _products.value.find { it.id == id }
    }
}