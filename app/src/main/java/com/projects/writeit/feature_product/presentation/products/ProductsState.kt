package com.projects.writeit.feature_product.presentation.products

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
import com.projects.writeit.feature_product.presentation.util.Lists

data class ProductsState(
    val products: List<Product> = Lists.initialProducts,
    val productsOrder: ProductOrder = ProductOrder.Date(OrderType.AscendingOrder),
    val addButtonIsExpanded: Boolean = false
)
