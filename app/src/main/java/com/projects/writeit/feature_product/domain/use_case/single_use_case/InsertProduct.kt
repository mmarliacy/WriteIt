package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.model.InvalidProductException
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository

class InsertProduct(
    private val repository: ProductRepository
){
        @Throws(InvalidProductException::class)
        suspend operator fun invoke(product: Product){
            if (product.name.isBlank()){
                throw InvalidProductException("Veuillez ajouter le nom du produit")
            }
            repository.insertProduct(product)
        }
}