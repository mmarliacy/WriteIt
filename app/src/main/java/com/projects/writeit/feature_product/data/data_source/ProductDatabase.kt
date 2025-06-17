package com.projects.writeit.feature_product.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projects.writeit.feature_product.domain.model.Product

@Database (
    entities = [Product::class],
    version = 2
)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao

    companion object {
        const val DATABASE_NAME = "products_db"
    }

}