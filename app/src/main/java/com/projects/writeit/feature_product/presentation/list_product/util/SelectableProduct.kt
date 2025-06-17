package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Product

data class SelectableProduct(
    val product: Product,
    val isChecked: Boolean = false
)