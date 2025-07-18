package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.repository.ItemRepository
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ItemOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case qui récupère uniquement les produits actifs (isArchived = false).
 *
 * La récupération se fait de manière ordonnée selon le choix de l'utilisateur :
 * par nom, date de création, ou catégorie (à venir).
 *
 * Ce tri est appliqué au moment de la connexion à l’UI pour permettre une
 * présentation des produits en fonction du critère sélectionné.
 */
class GetWishList(
    private val repository: ItemRepository
){
    operator fun invoke(
        pItemOrder: ItemOrder = ItemOrder.Date(OrderType.AscendingOrder)
    ): Flow<List<Item>>{
            return repository.getWishList().map { products ->
                when(pItemOrder.orderType){
                    is OrderType.AscendingOrder -> {
                        when(pItemOrder){
                            is ItemOrder.Name -> products.sortedBy{it.name.lowercase()}
                            is ItemOrder.Date -> products.sortedBy{it.timestamp}
                            is ItemOrder.Category -> products.sortedBy{it.category}
                        }
                    }
                    is OrderType.DescendingOrder -> {
                        when(pItemOrder){
                            is ItemOrder.Name -> products.sortedByDescending{it.name.lowercase()}
                            is ItemOrder.Date -> products.sortedByDescending{it.timestamp}
                            is ItemOrder.Category -> products.sortedByDescending{it.category}
                        }
                    }
                }

            }
    }
}