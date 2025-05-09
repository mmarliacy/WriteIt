package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
import com.projects.writeit.feature_product.presentation.util.Lists

data class ProductsState(
    val products: List<Product> = Lists.initialProducts,
    val deleteProducts: List<Product> = Lists.deletedProducts,
    val productsOrder: ProductOrder = ProductOrder.Date(OrderType.AscendingOrder),
    val addButtonIsExpanded: Boolean = false,
    val isDeletedProductIsVisible: Boolean = true,
    val addEditDialog: Boolean = false
)
