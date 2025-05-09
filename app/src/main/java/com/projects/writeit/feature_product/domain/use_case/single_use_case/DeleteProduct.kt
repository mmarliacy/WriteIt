package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository

class DeleteProduct(
    private val repository: ProductRepository
){
    suspend operator fun invoke(product: Product) {
        repository.deleteProduct(product)
    }
}