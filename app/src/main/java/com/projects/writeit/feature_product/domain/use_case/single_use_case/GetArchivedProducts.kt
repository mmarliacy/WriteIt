package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetArchivedProducts(
    private val repository: ProductRepository
) {
    operator fun invoke(
        productOrder: ProductOrder = ProductOrder.Date(OrderType.AscendingOrder)
    ): Flow<List<Product>> {
        return repository.getArchivedProducts().map { archivedProducts ->
            when (productOrder.orderType){
                is OrderType.AscendingOrder -> {
                    when(productOrder){
                        is ProductOrder.Title -> archivedProducts.sortedBy{it.name.lowercase()}
                        is ProductOrder.Date -> archivedProducts.sortedBy{it.timestamp}
                        is ProductOrder.Category -> archivedProducts.sortedBy{it.category}
                    }
                }
                is OrderType.DescendingOrder -> {
                    when(productOrder){
                        is ProductOrder.Title -> archivedProducts.sortedByDescending{it.name.lowercase()}
                        is ProductOrder.Date -> archivedProducts.sortedByDescending{it.timestamp}
                        is ProductOrder.Category -> archivedProducts.sortedByDescending{it.category}
                    }
                }
            }
        }
    }
}