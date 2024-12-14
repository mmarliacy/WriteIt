package com.projects.writeit.feature_product.domain.model

data class Product (
    val id :Int? = null,
    val name: String,
    val quantity:Int? = null,
    val categoryId:Int? = null
) {
}