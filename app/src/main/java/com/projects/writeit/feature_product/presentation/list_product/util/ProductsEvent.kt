package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.util.ProductOrder

sealed class ProductsEvent {
    data class Order(val productOrder: ProductOrder) : ProductsEvent()
    data class DeleteProduct(val product: Product) : ProductsEvent()
    data class ArchiveProduct(val product: Product): ProductsEvent()
    data class DisArchiveProduct(val product: Product): ProductsEvent()
    data class ToggleProductSelection(val productId: Int, val isChecked: Boolean): ProductsEvent()
    data class DeleteSelectedProducts(val productsToDelete: List<SelectableProduct>): ProductsEvent()
    data object ToggleProductSelectionMode : ProductsEvent()
    data object RestoreProduct : ProductsEvent()
    data object RestoreAllProducts : ProductsEvent()

}