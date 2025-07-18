package com.projects.writeit.feature_product.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projects.writeit.feature_product.domain.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM item_table WHERE isInTheCaddy = 0")
    fun getWishList() : Flow<List<Item>>

    @Query("SELECT * FROM item_table WHERE isInTheCaddy = 1")
    fun getCaddyList() : Flow<List<Item>>

    @Query("SELECT * FROM item_table WHERE id = :id")
    suspend fun getItemById(id : Int): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(pItem: Item)

    @Delete
    suspend fun deleteItem(pItem: Item)
}