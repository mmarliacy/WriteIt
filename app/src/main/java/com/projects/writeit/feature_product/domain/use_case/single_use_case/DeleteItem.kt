package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.repository.ItemRepository

/**
 * Use case qui permet de supprimer un produit de la base de données locale.
 */
class DeleteItem (
    private val repository: ItemRepository
){
    suspend operator fun invoke(pItem: Item) {
        repository.deleteItem(pItem)
    }
}