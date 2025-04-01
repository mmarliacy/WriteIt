package com.projects.writeit.feature_product.data.repository

import com.projects.writeit.feature_product.data.data_source.ProductDao
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {
    override fun getProducts(): Flow<List<Product>> {
        return productDao.getProducts()
    }

    override suspend fun getProductById(id: Int): Product? {
        return productDao.getProductById(id)
    }

    override suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)    }

}