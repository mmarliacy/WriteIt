package com.projects.writeit.feature_product.data.repository

import com.projects.writeit.feature_product.data.data_source.ItemDao
import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl(
    private val itemDao: ItemDao
) : ItemRepository {

    override fun getWishList(): Flow<List<Item>> {
        return itemDao.getWishList()
    }

    override fun getCaddyList(): Flow<List<Item>> {
        return itemDao.getCaddyList()
    }

    override suspend fun getItemById(id: Int): Item? {
        return itemDao.getItemById(id)
    }

    override suspend fun insertItem(pItem: Item) {
        itemDao.insertItem(pItem)
    }

    override suspend fun deleteItem(pItem: Item) {
        itemDao.deleteItem(pItem)
    }
}