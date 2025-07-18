package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case qui récupère uniquement les produits archivés, dont le paramètre "isArchived" est vrai.
 */
class GetCaddyList(
    private val repository: ItemRepository
) {
    operator fun invoke(): Flow<List<Item>> {
        return repository.getCaddyList()
    }
}