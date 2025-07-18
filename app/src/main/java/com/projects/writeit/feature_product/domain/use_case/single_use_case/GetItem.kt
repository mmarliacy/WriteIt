package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.repository.ItemRepository

/**
 * Use case qui récupère un seul et unique produit par son "id".
 */
class GetItem (
    private val repository: ItemRepository
){

    suspend operator fun invoke(id: Int) : Item? {
        return repository.getItemById(id)
    }
}