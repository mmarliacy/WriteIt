package com.projects.writeit.feature_product.domain.repository
import com.projects.writeit.feature_product.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    fun getWishList() : Flow<List<Item>>

    fun getCaddyList() : Flow<List<Item>>

    suspend fun getItemById(id : Int): Item?

    suspend fun insertItem(pItem: Item)

    suspend fun deleteItem(pItem: Item)
}