package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case qui récupère uniquement les produits archivés, dont le paramètre "isArchived" est vrai.
 */
class GetArchivedProducts(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getArchivedProducts()
    }
}