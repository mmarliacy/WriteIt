package com.projects.writeit.feature_product.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projects.writeit.feature_product.domain.model.Item

@Database (
    entities = [Item::class],
    version = 2
)
abstract class ItemDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao

    companion object {
        const val DATABASE_NAME = "items_db"
    }

}