package com.projects.writeit.feature_product.presentation.list_product.components.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.ui.graphics.vector.ImageVector

enum class TabItem(
    // val tabName: String,
    val icon: ImageVector
) {
    ShopList(
     //   tabName = "Liste",
        icon = Icons.Filled.FormatListNumbered
    ),
    ArchivedList(
      //  tabName = "Archivés",
        icon = Icons.Filled.FormatListNumbered
    )
}