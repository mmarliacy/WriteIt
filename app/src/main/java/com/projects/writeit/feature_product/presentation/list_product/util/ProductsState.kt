package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ItemOrder

/**
 * Classe représentant l'état de l'écran de gestion des produits.
 *
 * Elle centralise toutes les informations nécessaires pour afficher et interagir avec l'UI,
 * conformément à l'architecture MVVM + State.
 *
 */
data class ProductsState(
    val selectableActiveProducts: List<SelectableProduct> = emptyList(),
    val pArchivedItems: List<Item> = emptyList(),
    val productsOrder: ItemOrder = ItemOrder.Date(OrderType.AscendingOrder),
    val isDeletedProductIsVisible: Boolean = true,
    val showBottomSheet : Boolean = false,
    val isSelectionMode: Boolean = false,
    val buttonDeleteIsVisible: Boolean = false,
    val sortDropDownExpanded: Boolean = false
)
