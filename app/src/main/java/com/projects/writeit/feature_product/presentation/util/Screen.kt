package com.projects.writeit.feature_product.presentation.util

sealed class Screen (
    val route : String
){
    object ProductsScreen: Screen("products_screen")
    object AddEditProductScreen : Screen("add_edit_product_screen")
}