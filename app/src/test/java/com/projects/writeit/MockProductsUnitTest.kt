package com.projects.writeit

import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.feature_product.domain.repository.ItemRepository
import com.projects.writeit.feature_product.domain.use_case.single_use_case.InsertItem
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetWishList
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ItemOrder
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MockProductsUnitTest {

    private lateinit var repository: ItemRepository
    private lateinit var fItemOrder : ItemOrder
    private lateinit var fGetWishList: GetWishList
    private lateinit var fInsertProducts: InsertItem
    private val fFakeItems = listOf(
        Item(1, "Chocolat", 1, 2.50,"Petit déjeuner", 1234, true),
        Item(2, "Beurre", 2, 1.70, "Laitier", 1234, false),
        Item(3, "Farine", 2, 1.10, "Patisserie", 1234, false),
        Item(4, "Saucisses Cocktails", 3, 2.60, "Snacks", 1234, false),

        )

    @Before
    fun init() {
        fItemOrder = ItemOrder.Date(OrderType.DescendingOrder)
        repository = mockk(relaxed = true)
        coEvery { repository.getWishList() } returns flowOf(fFakeItems)
        fGetWishList = GetWishList(repository)
        fInsertProducts = InsertItem(repository)

    }

    @Test
    fun getProductsFromRepo() {
        runTest{
            val result = fGetWishList(fItemOrder).first()
            assertEquals(result.size,4)
        }

    }

    @Test
    fun insertProduct(){
        runTest {
            val newItem = Item(
                id = 0,
                name = "Café",
                quantity = 2,
                price = 1.50,
                category = "Petit déjeuner",
                timestamp = 1234,
                isInTheCaddy = false
            )

            val updatedList = fFakeItems + newItem
            coEvery {
                repository.getWishList()
            } returns flowOf(updatedList)


            val productsList = fGetWishList(fItemOrder).first()

            assertEquals(5, productsList.size,)
            assertTrue(productsList.contains(newItem))

        }
    }
}