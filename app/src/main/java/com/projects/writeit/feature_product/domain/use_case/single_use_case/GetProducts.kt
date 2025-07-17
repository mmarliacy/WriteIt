package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
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
class GetProducts(
    private val repository: ProductRepository
){
    operator fun invoke(
        productOrder: ProductOrder = ProductOrder.Date(OrderType.AscendingOrder)
    ): Flow<List<Product>>{
            return repository.getActiveProducts().map { products ->
                when(productOrder.orderType){
                    is OrderType.AscendingOrder -> {
                        when(productOrder){
                            is ProductOrder.Name -> products.sortedBy{it.name.lowercase()}
                            is ProductOrder.Date -> products.sortedBy{it.timestamp}
                            is ProductOrder.Category -> products.sortedBy{it.category}
                        }
                    }
                    is OrderType.DescendingOrder -> {
                        when(productOrder){
                            is ProductOrder.Name -> products.sortedByDescending{it.name.lowercase()}
                            is ProductOrder.Date -> products.sortedByDescending{it.timestamp}
                            is ProductOrder.Category -> products.sortedByDescending{it.category}
                        }
                    }
                }

            }
    }
}