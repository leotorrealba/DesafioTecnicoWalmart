package com.leotorrealba.desafiotecnico.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.leotorrealba.desafiotecnico.presentation.viewmodel.CartViewModel
import com.leotorrealba.desafiotecnico.presentation.viewmodel.ProductsViewModel
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    productId: Int,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    LaunchedEffect(productId) {
        productsViewModel.loadProductById(productId)
    }

    val product by productsViewModel.currentProduct.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
        }
    ) { paddingValues ->
        product?.let { product ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )
                Text(text = product.title, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Precio: $${product.price}", style = MaterialTheme.typography.titleMedium)
                Text(text = product.description, style = MaterialTheme.typography.bodyLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(rating = product.rating.rate.toFloat())

                    FloatingActionButton(
                        onClick = {
                            cartViewModel.addToCart(product)
                            navController.navigateUp()
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .align(Alignment.Bottom)
                            .padding(8.dp),
                        containerColor = Color(0xFFFF9800)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Agregar al carrito", tint = Color.White)
                    }
                }
            }
        }
    }
}


@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Int = 24,
    starShape: Shape = MaterialTheme.shapes.small,
    starColor: Color = Color.Blue
) {
    Row(modifier = modifier) {
        repeat(starCount) { index ->
            val filled = rating > index
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (filled) starColor else starColor.copy(alpha = 0.2f),
                modifier = Modifier.size(starSize.dp)
            )
        }
    }
}
