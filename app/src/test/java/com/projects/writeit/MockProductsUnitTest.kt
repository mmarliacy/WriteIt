package com.projects.writeit

import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.repository.ProductRepository
import com.projects.writeit.feature_product.domain.use_case.single_use_case.InsertProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProducts
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
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

    private lateinit var repository: ProductRepository
    private lateinit var productOrder : ProductOrder
    private lateinit var getProducts: GetProducts
    private lateinit var insertProducts: InsertProduct
    private val fakeProducts = listOf(
        Product(1, "Chocolat", 1, 2.50,"Petit déjeuner", 1234, true),
        Product(2, "Beurre", 2, 1.70, "Laitier", 1234, false),
        Product(3, "Farine", 2, 1.10, "Patisserie", 1234, false),
        Product(4, "Saucisses Cocktails", 3, 2.60, "Snacks", 1234, false),

        )

    @Before
    fun init() {
        productOrder = ProductOrder.Date(OrderType.DescendingOrder)
        repository = mockk(relaxed = true)
        coEvery { repository.getActiveProducts() } returns flowOf(fakeProducts)
        getProducts = GetProducts(repository)
        insertProducts = InsertProduct(repository)

    }

    @Test
    fun getProductsFromRepo() {
        runTest{
            val result = getProducts(productOrder).first()
            assertEquals(result.size,4)
        }

    }

    @Test
    fun insertProduct(){
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

            val updatedList = fakeProducts + newProduct
            coEvery {
                repository.getActiveProducts()
            } returns flowOf(updatedList)


            val productsList = getProducts(productOrder).first()

            assertEquals(5, productsList.size,)
            assertTrue(productsList.contains(newProduct))

        }
    }
}