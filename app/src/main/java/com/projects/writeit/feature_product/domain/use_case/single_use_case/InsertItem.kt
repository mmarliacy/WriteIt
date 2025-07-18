package com.projects.writeit.feature_product.domain.use_case.single_use_case

import com.projects.writeit.feature_product.domain.util.InvalidItemException
import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.repository.ItemRepository

class InsertItem(
    private val repository: ItemRepository
) {
    @Throws(InvalidItemException::class)
    suspend operator fun invoke(pItem: Item) {
        when {
            pItem.name.isBlank() -> throw InvalidItemException("Veuillez renseigner un nom pour votre produit")
            pItem.price <= 0 -> throw InvalidItemException("Le prix du produit doit être supérieur à 0 euros")
            //product.quantity >= 0 -> throw InvalidProductException("La quantité doit être supérieur ou égale à 0")
            else -> repository.insertItem(pItem)
        }
    }
}