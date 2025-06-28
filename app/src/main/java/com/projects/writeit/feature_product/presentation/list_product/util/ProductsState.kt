package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder

/**
 * Classe représentant l'état de l'écran de gestion des produits.
 *
 * Elle centralise toutes les informations nécessaires pour afficher et interagir avec l'UI,
 * conformément à l'architecture MVVM + State.
 *
 */
data class ProductsState(
    val selectableActiveProducts: List<SelectableProduct> = emptyList(),
    val archivedProducts: List<Product> = emptyList(),
    val productsOrder: ProductOrder = ProductOrder.Date(OrderType.AscendingOrder),
    val isDeletedProductIsVisible: Boolean = true,
    val showBottomSheet : Boolean = false,
    val isSelectionMode: Boolean = false,
    val buttonDeleteIsVisible: Boolean = false,
    val sortDropDownExpanded: Boolean = false
)
