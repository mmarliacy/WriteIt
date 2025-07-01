package com.projects.writeit.di

import android.app.Application
import androidx.room.Room
import com.projects.writeit.feature_product.data.data_source.ProductDatabase
import com.projects.writeit.feature_product.data.repository.ProductRepositoryImpl
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import com.projects.writeit.feature_product.domain.use_case.single_use_case.InsertProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.DeleteProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProducts
import com.projects.writeit.feature_product.domain.use_case.ProductUseCases
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetArchivedProducts
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProduct
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
        ).
        fallbackToDestructiveMigration(false).build()
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
            getArchivedProducts = GetArchivedProducts(repository),
            deleteProduct = DeleteProduct(repository),
            insertProduct = InsertProduct(repository),
            getProduct = GetProduct(repository)
        )
    }
}