package com.leotorrealba.desafiotecnico.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.leotorrealba.desafiotecnico.presentation.viewmodel.ProductsViewModel
import com.leotorrealba.desafiotecnico.presentation.ui.common.DrawerContent
import com.leotorrealba.desafiotecnico.presentation.ui.common.ErrorHandler
import com.leotorrealba.desafiotecnico.presentation.ui.common.FeaturedProductCard
import com.leotorrealba.desafiotecnico.presentation.ui.common.LoadingProgress
import com.leotorrealba.desafiotecnico.presentation.ui.common.ProductCard
import com.leotorrealba.desafiotecnico.presentation.viewmodel.CartViewModel
import com.leotorrealba.desafiotecnico.utilities.UiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val uiState by productsViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        productsViewModel.fetch()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(productsViewModel, navController)
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Desafio Tecnico") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            val cartItemsCount by cartViewModel.totalItemCount.collectAsState()
                            IconButton(onClick = { navController.navigate("cart") }) {
                                BadgedBox(
                                    badge = {
                                        if (cartItemsCount > 0) {
                                            Badge { Text(cartItemsCount.toString()) }
                                        }
                                    }
                                ) {
                                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                                }
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    when (uiState) {
                        is UiState.Loading -> {
                            LoadingProgress()
                        }

                        is UiState.Success -> {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    productsViewModel.featuredProduct.let { product ->
                                        product.value?.let { FeaturedProductCard(
                                            it,
                                            onAddToCart = { cartViewModel.addToCart(it) },
                                            onCardClick = { navController.navigate("productDetail/${it.id}") }
                                        )}
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(2),
                                        contentPadding = PaddingValues(10.dp),
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        items(productsViewModel.products.value.size) { product ->
                                            ProductCard(productsViewModel.products.value[product],
                                                onAddToCart = {
                                                    cartViewModel.addToCart(productsViewModel.products.value[product])
                                                    productsViewModel.addProductToList(productsViewModel.products.value[product])
                                                },
                                                onCardClick = { navController.navigate("productDetail/${productsViewModel.products.value[product].id}") }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        is UiState.Error -> {
                            ErrorHandler(
                                message = (uiState as UiState.Error).message,
                                productsViewModel
                            )
                        }

                        else -> {}

                    }
                }
            )
        }
    )
}