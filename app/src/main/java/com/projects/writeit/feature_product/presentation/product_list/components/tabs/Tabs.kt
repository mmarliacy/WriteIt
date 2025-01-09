package com.projects.writeit.feature_product.presentation.product_list.components.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tabs(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tabName: String
) {
    ShopList(
        unselectedIcon = Icons.Outlined.ShoppingCart,
        selectedIcon = Icons.Filled.ShoppingCart,
        tabName = "Liste"
    ),
    ArchivedList(
        unselectedIcon = Icons.Outlined.Add,
        selectedIcon = Icons.Filled.Add,
        tabName = "Archivés"
    )
}