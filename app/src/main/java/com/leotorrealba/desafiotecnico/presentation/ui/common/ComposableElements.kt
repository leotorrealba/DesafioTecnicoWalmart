package com.leotorrealba.desafiotecnico.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.leotorrealba.desafiotecnico.domain.model.Product
import com.leotorrealba.desafiotecnico.presentation.viewmodel.ProductsViewModel

@Composable
fun DrawerContent(
    viewModel: ProductsViewModel,
    navController: NavHostController
) {
    val categories by viewModel.categories.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE0B2))
            .padding(top = 50.dp)
    ) {
        Text(
            text = "CATEGORIAS",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        LazyColumn {
            item {
                NavigationDrawerItem(
                    label = { Text("INICIO".uppercase()) },
                    selected = false,
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            }

            items(categories) { category ->
                NavigationDrawerItem(
                    label = { Text(category.uppercase()) },
                    selected = false,
                    onClick = { navController.navigate("productsByCategory/$category") },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun LoadingProgress() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Cargando...")
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()

            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onAddToCart: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFE0B2)
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.title, maxLines = 2, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$${product.price}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            FloatingActionButton(
                onClick = onAddToCart,
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.End)
                    .padding(8.dp),
                containerColor = Color(0xFFFF9800)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar al carrito", tint = Color.White)
            }
        }
    }
}

@Composable
fun FeaturedProductCard(
    product: Product,
    onAddToCart: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF001F3F),
            contentColor = Color.White
        )
    )  {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "DESTACADO", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                Text(text = product.title, maxLines = 2, style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$${product.price}", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }
            FloatingActionButton(
                onClick = onAddToCart,
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


@Composable
fun ErrorHandler(
    message: String,
    productsViewModel: ProductsViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { productsViewModel.loadAgain() }) {
                Text(text = "Volver a intentar")
            }
        }
    }
}