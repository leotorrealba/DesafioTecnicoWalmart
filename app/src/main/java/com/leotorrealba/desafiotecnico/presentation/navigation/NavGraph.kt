package com.leotorrealba.desafiotecnico.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.leotorrealba.desafiotecnico.presentation.ui.cart.CartScreen
import com.leotorrealba.desafiotecnico.presentation.ui.home.HomeScreen
import com.leotorrealba.desafiotecnico.presentation.ui.pbc.ProductsByCategoryScreen
import com.leotorrealba.desafiotecnico.presentation.ui.detail.ProductDetailScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("productsByCategory/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            category?.let { ProductsByCategoryScreen(navController, it) }
        }
        composable("cart") {
            CartScreen(navController)
        }
        composable(
            "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(navController = navController, productId = productId)
        }
    }
}
