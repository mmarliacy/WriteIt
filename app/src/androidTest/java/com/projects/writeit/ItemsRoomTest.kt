package com.projects.writeit

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.projects.writeit.feature_product.data.data_source.ItemDao
import com.projects.writeit.feature_product.data.data_source.ItemDatabase
import com.projects.writeit.feature_product.data.repository.ItemRepositoryImpl
import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.repository.ItemRepository
import com.projects.writeit.feature_product.domain.use_case.single_use_case.DeleteItem
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetItem
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetWishList
import com.projects.writeit.feature_product.domain.use_case.single_use_case.InsertItem
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ItemOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ItemsRoomTest {

    private lateinit var db: ItemDatabase
    private lateinit var dao: ItemDao
    private lateinit var repository: ItemRepository
    private lateinit var fItemOrder: ItemOrder
    private lateinit var fNewItem: Item

    // Cas d'utilisations appelés pour tester la consultation, l'ajout,
    // la suppression, la mise à jour d'un produit
    private lateinit var fGetWishList: GetWishList
    private lateinit var fInsertItem: InsertItem
    private lateinit var fDeleteItem: DeleteItem
    private lateinit var fGetItem: GetItem

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(
            context,
            ItemDatabase::class.java,
            "database"
        ).allowMainThreadQueries().build()
        fItemOrder = ItemOrder.Date(OrderType.AscendingOrder)
        dao = db.itemDao
        repository = ItemRepositoryImpl(dao)

        // Uses cases
        fGetWishList = GetWishList(repository)
        fInsertItem = InsertItem(repository)
        fDeleteItem = DeleteItem(repository)
        fGetItem = GetItem(repository)


    }

    @Test
    fun getProducts() {
        runTest {
            val productList = fGetWishList(fItemOrder).first()
            assertTrue(productList.isEmpty())
        }
    }

    @Test
    fun insertNewProduct() {
        runTest {

            fNewItem = Item(
                id = 0,
                name = "Café",
                quantity = 2,
                price = 1.50,
                category = "Petit déjeuner",
                timestamp = 1234,
                isInTheCaddy = false
            )

            // Ajouter le produit dans la base de données de test.
            fInsertItem(fNewItem)
            // Récupérer le premier élément émis par le Flow
            val productList = fGetWishList(fItemOrder).first()

                assertTrue(productList.isNotEmpty())
            // Prouver que c'est le bon élément peu importe l'ordre
                assertTrue(productList.any {it.name == "Café"
                })
        }
    }

    @Test
    fun deleteNewProduct(){
        runTest {
            // New Product
            fNewItem = Item(
                id = 0,
                name = "Café",
                quantity = 2,
                price = 1.50,
                category = "Petit déjeuner",
                timestamp = 1234,
                isInTheCaddy = false
            )
            // Ajouter le produit dans la base de données de test.
            fInsertItem(fNewItem)
            // Récupérer la liste après ajout dans la base de donnée locale
            val productList = fGetWishList(fItemOrder).first()

            // Vérifier que l'élément a été ajouté puis le supprimer.
            assertEquals(1, productList.size)
            fDeleteItem(fNewItem)

            // Récupérer la liste suite à la suppression
            // puis vérifier si elle est vide.
            val updateList = fGetWishList(fItemOrder).first()
            assertEquals(0, updateList.size)
            assertFalse(updateList.contains(fNewItem))
        }
    }
}