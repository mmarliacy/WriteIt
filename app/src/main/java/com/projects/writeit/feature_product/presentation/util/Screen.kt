package com.projects.writeit.feature_product.presentation.util

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object ProductsScreen: Screen()
    @Serializable
    data object AddEditProductScreen : Screen()
}