package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository

/**
 * Use case qui permet de supprimer un produit de la base de données locale.
 */
class DeleteProduct(
    private val repository: ProductRepository
){
    suspend operator fun invoke(product: Product) {
        repository.deleteProduct(product)
    }
}