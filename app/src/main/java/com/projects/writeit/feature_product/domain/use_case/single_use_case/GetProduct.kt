package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository

/**
 * Use case qui récupère un seul et unique produit par son "id".
 */
class GetProduct (
    private val repository: ProductRepository
){

    suspend operator fun invoke(id: Int) : Product? {
        return repository.getProductById(id)
    }
}