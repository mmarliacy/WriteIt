package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.util.ItemOrder

/**
 * Classe scellée représentant tous les événements possibles liés aux produits.
 *
 * Ces événements sont utilisés pour déclencher des actions spécifiques dans le ViewModel.
 *
 * Cela inclut : la gestion du tri, la suppression, l'archivage, la restauration,
 * la sélection multiple, et l'affichage du dialogue d'ajout.
 */
sealed class ProductsEvent {
    data class Order(val pItemOrder: ItemOrder) : ProductsEvent()
    data class ArchiveProduct(val pItem: Item): ProductsEvent()
    data class DisArchiveProduct(val pItem: Item): ProductsEvent()
    data class ToggleProductSelection(val productId: Int, val isChecked: Boolean): ProductsEvent()
    data class DeleteSelectedProducts(val productsToDelete: List<SelectableProduct>): ProductsEvent()
    data object ToggleSortDropDownMenu : ProductsEvent()
    data object ToggleProductSelectionMode : ProductsEvent()
    data object RestoreProduct : ProductsEvent()
    data object RestoreAllProducts : ProductsEvent()
    data object ToggleBottomDialog : ProductsEvent()
}