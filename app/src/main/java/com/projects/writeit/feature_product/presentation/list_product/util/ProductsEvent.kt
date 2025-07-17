package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.util.ProductOrder

/**
 * Classe scellée représentant tous les événements possibles liés aux produits.
 *
 * Ces événements sont utilisés pour déclencher des actions spécifiques dans le ViewModel.
 *
 * Cela inclut : la gestion du tri, la suppression, l'archivage, la restauration,
 * la sélection multiple, et l'affichage du dialogue d'ajout.
 */
sealed class ProductsEvent {
    data class Order(val productOrder: ProductOrder) : ProductsEvent()
    data class ArchiveProduct(val product: Product): ProductsEvent()
    data class DisArchiveProduct(val product: Product): ProductsEvent()
    data class ToggleProductSelection(val productId: Int, val isChecked: Boolean): ProductsEvent()
    data class DeleteSelectedProducts(val productsToDelete: List<SelectableProduct>): ProductsEvent()
    data object ToggleSortDropDownMenu : ProductsEvent()
    data object ToggleProductSelectionMode : ProductsEvent()
    data object RestoreProduct : ProductsEvent()
    data object RestoreAllProducts : ProductsEvent()
    data object ToggleBottomDialog : ProductsEvent()
}