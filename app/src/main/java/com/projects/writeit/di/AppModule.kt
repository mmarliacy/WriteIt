package com.projects.writeit.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projects.writeit.feature_product.data.data_source.ProductDatabase
import com.projects.writeit.feature_product.data.repository.ProductRepositoryImpl
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import com.projects.writeit.feature_product.domain.use_case.DeleteProduct
import com.projects.writeit.feature_product.domain.use_case.GetProducts
import com.projects.writeit.feature_product.domain.use_case.ProductUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductDatabase(app: Application): ProductDatabase {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            ProductDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesProductRepository(db: ProductDatabase): ProductRepository {
        return ProductRepositoryImpl(db.productDao)
    }

    @Provides
    @Singleton
    fun providesProductUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getProducts = GetProducts(repository),
            deleteProduct = DeleteProduct(repository)
        )
    }
}