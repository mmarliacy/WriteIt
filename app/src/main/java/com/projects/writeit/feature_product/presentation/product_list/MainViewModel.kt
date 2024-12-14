package com.projects.writeit.feature_product.presentation.product_list


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.projects.writeit.feature_product.domain.model.Category
import com.projects.writeit.feature_product.domain.model.Product

class MainViewModel : ViewModel() {

    private val _initialProducts = mutableStateListOf(
        Product(1, "Chocolat", 2, 2),
        Product(2, "Pomme",  10, 1),
        Product(3, "Saucisson",10, 2),
        Product(4, "Beurre", 10, 3),
        Product(5, "Farine", 10, 4),
        Product(6, "Lardon", 10, 5),
        Product(7, "Pain", 10, 2),
        Product(8, "Levure boulangère", 10, 3),
        Product(9, "Huile", 10, 4),
        Product(10, "Frites", 10, 5),
        Product(11, "Pâtes", 10, 6),
        Product(12, "Cassoulet", 10, 7),
        Product(13, "Ratatouille", 10, 8),
        Product(14, "Biscuits",  10, 10),
        Product(15, "Madeleines",  10, 8),
        Product(16, "Café", 10, 9),
        Product(17, "Cappuccino", 10, 0),
        Product(18, "Banana",  10, 1),
        Product(19, "Coconut", 10, 3),
        Product(20, "Sauce tomato", 10, 1),
        Product(21, "Mayonnaise", 10, 2)
    )

    private val _deletedProducts = mutableStateListOf<Product>()

    private val _categories = mutableStateListOf(
        Category(1, "Nourriture"),
        Category(2, "Divers"),
        Category(3, "Maison"),
        Category(4, "Petits achats"),
        Category(5, "Informatique"),
        Category(6, "Loisirs"),
        Category(7, "Soin du corps"),
        Category(8, "Bricolage"),
        Category(9, "Accessoires"),
        Category(10, "Animaux"),
        Category(11, "Sans catégories"),
    )


    val initialProducts: List<Product> = _initialProducts
    val deleteProducts: List<Product> = _deletedProducts
    val categories:List<Category> = _categories

    fun addNewProduct(product: Product) {
        _initialProducts.add(product)
    }

    fun deleteProduct(product: Product) {
        _deletedProducts.add(product)
        _initialProducts.remove(product)
    }

    fun cancelDeletion(product: Product) {
        _initialProducts.add(product)
        _deletedProducts.remove(product)
    }
}