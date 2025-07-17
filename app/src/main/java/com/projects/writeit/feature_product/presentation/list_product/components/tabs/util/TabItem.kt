package com.projects.writeit.feature_product.presentation.list_product.components.tabs.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Enumération listant les éléments faisant parti du TabRow et synchronisés avec l'Horizontal Pager.
 *
 * Il s'agit de regrouper ensemble les onglets :
 * - ShopList représenté par `Icons.Filled.FormatListNumbered`
 * - ArchivedList représenté par `Icons.Filled.Checklist`
 *
 * Cette approche permet de centraliser la configuration des onglets
 * et de faciliter leur évolution (ajout, modification, icône...).
 *
 * @param icon Icône affiché pour l'onglet correspondant.
 */
enum class TabItem(val icon: ImageVector) {
    ShopList(Icons.Filled.FormatListNumbered),
    ArchivedList(Icons.Filled.Checklist)
}