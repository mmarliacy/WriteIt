package com.projects.writeit.feature_product.domain.repository
import com.projects.writeit.feature_product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts() : Flow<List<Product>>

    suspend fun getProductById(id : Int): Product?

    suspend fun insertProduct(product: Product)

    suspend fun deleteProduct(product: Product)
}