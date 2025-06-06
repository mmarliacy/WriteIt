package com.projects.writeit.feature_product.domain.util

data class ProductTextFieldState (
    val hint: String = "",
    val text: String = "",
    val quantityText: String = "",
    val priceText : String = "",
    val quantity : Int = 0,
    val price : Double = 0.0,
    val isHintVisible: Boolean = true
)
