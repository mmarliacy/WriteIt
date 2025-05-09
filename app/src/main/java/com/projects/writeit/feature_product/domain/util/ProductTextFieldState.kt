package com.projects.writeit.feature_product.domain.util

data class ProductTextFieldState (
    val text: String = "",
    val hint: String = "",
    val quantity : Int = 0,
    val isHintVisible: Boolean = true
)
