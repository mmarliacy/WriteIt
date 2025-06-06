package com.projects.writeit.feature_product.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projects.writeit.feature_product.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table WHERE isArchived = 0")
    fun getActiveProducts() : Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE isArchived = 1")
    fun getArchivedProducts() : Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE id = :id")
    suspend fun getProductById(id : Int): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}