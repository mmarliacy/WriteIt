package com.projects.writeit.feature_product.presentation.products

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.util.ProductOrder

sealed class ProductsEvent {
    data class Order(val productOrder: ProductOrder) : ProductsEvent()
    data class DeleteProduct(val product: Product) : ProductsEvent ()
    data object RestoreProduct: ProductsEvent()
}