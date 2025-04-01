package com.projects.writeit.feature_product.domain.use_case

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository

class DeleteProduct(
    private val productRepository: ProductRepository
){
    suspend operator fun invoke(product: Product) {
        productRepository.deleteProduct(product)
    }
}