package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
                            is ProductOrder.Title -> products.sortedBy{it.name.lowercase()}
                            is ProductOrder.Date -> products.sortedBy{it.timestamp}
                            is ProductOrder.Category -> products.sortedBy{it.category}
                        }
                    }
                    is OrderType.DescendingOrder -> {
                        when(productOrder){
                            is ProductOrder.Title -> products.sortedByDescending{it.name.lowercase()}
                            is ProductOrder.Date -> products.sortedByDescending{it.timestamp}
                            is ProductOrder.Category -> products.sortedByDescending{it.category}
                        }
                    }
                }

            }
    }
}