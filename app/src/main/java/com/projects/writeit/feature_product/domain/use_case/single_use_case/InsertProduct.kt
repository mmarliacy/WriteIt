package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.util.InvalidProductException
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository

class InsertProduct(
    private val repository: ProductRepository
) {
    @Throws(InvalidProductException::class)
    suspend operator fun invoke(product: Product) {
        when {
            product.name.isBlank() -> throw InvalidProductException("Veuillez renseigner un nom pour votre produit")
            product.price <= 0 -> throw InvalidProductException("Le prix du produit doit être supérieur à 0 euros")
            //product.quantity >= 0 -> throw InvalidProductException("La quantité doit être supérieur ou égale à 0")
            else -> repository.insertProduct(product)
        }
    }
}