package com.projects.writeit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.projects.writeit.feature_product.data.data_source.ProductDao
import com.projects.writeit.feature_product.data.data_source.ProductDatabase
import com.projects.writeit.feature_product.data.repository.ProductRepositoryImpl
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import com.projects.writeit.feature_product.domain.use_case.single_use_case.DeleteProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProducts
import com.projects.writeit.feature_product.domain.use_case.single_use_case.InsertProduct
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductsRoomTest {

    private lateinit var db: ProductDatabase
    private lateinit var dao: ProductDao
    private lateinit var repository: ProductRepository
    private lateinit var productOrder: ProductOrder

    // Cas d'utilisation pour tester la consultation, l'ajout,
    // la suppression, la mise à jour d'un produit
    private lateinit var getProducts: GetProducts
    private lateinit var insertProduct: InsertProduct
    private lateinit var deleteProduct: DeleteProduct
    private lateinit var getProduct: GetProduct

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "database"
        ).allowMainThreadQueries().build()
        productOrder = ProductOrder.Date(OrderType.AscendingOrder)
        dao = db.productDao
        repository = ProductRepositoryImpl(dao)

        // Uses cases
        getProducts = GetProducts(repository)
        insertProduct = InsertProduct(repository)
        deleteProduct = DeleteProduct(repository)
        getProduct = GetProduct(repository)
    }

    @Test
    fun getProducts() {
        runTest {
            val productList = getProducts(productOrder).first()
            assertTrue(productList.isEmpty())
        }
    }

    @Test
    fun insertNewProduct() {
        runTest {

            val newProduct = Product(
                id = 0,
                name = "Café",
                quantity = 2,
                price = 1.50,
                category = "Petit déjeuner",
                timestamp = 1234,
                isArchived = false
            )
            // Ajouter le produit dans la base de données de test.
            insertProduct(newProduct)
            // Récupérer le premier élément émis par le Flow
            val productList = getProducts(productOrder).first()


                assertTrue(productList.isNotEmpty())
            // Prouver que c'est le bon élément peu importe l'ordre
                assertTrue(productList.any {it.name == "Café"
                })
        }
    }
}