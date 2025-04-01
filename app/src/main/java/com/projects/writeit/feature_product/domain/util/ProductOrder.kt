package com.projects.writeit.feature_product.domain.util

sealed class ProductOrder(val orderType: OrderType){
    class Title (orderType: OrderType): ProductOrder(orderType)
    class Date (orderType: OrderType): ProductOrder(orderType)
    class Category (orderType: OrderType): ProductOrder(orderType)
}