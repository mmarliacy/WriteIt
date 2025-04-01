package com.projects.writeit.feature_product.domain.util

sealed class OrderType {
    data object AscendingOrder : OrderType()
    data object DescendingOrder : OrderType()

}