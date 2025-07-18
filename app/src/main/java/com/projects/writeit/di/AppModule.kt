package com.projects.writeit.di

import android.app.Application
import androidx.room.Room
import com.projects.writeit.feature_product.data.data_source.ItemDatabase
import com.projects.writeit.feature_product.data.repository.ItemRepositoryImpl
import com.projects.writeit.feature_product.domain.repository.ItemRepository
import com.projects.writeit.feature_product.domain.use_case.single_use_case.InsertItem
import com.projects.writeit.feature_product.domain.use_case.single_use_case.DeleteItem
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetWishList
import com.projects.writeit.feature_product.domain.use_case.ProductUseCases
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetCaddyList
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetItem
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
    fun provideProductDatabase(app: Application): ItemDatabase {
        return Room.databaseBuilder(
            app,
            ItemDatabase::class.java,
            ItemDatabase.DATABASE_NAME
        ).
        fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun providesProductRepository(db: ItemDatabase): ItemRepository {
        return ItemRepositoryImpl(db.itemDao)
    }

    @Provides
    @Singleton
    fun providesProductUseCases(repository: ItemRepository): ProductUseCases {
        return ProductUseCases(
            pGetWishList = GetWishList(repository),
            pGetCaddyList = GetCaddyList(repository),
            pDeleteItem = DeleteItem(repository),
            pInsertItem = InsertItem(repository),
            pGetItem = GetItem(repository)
        )
    }
}