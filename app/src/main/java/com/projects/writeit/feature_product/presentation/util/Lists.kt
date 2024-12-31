package com.projects.writeit.feature_product.presentation.util

import androidx.compose.runtime.mutableStateListOf
import com.projects.writeit.feature_product.domain.model.Category
import com.projects.writeit.feature_product.domain.model.Product

object Lists {

     val _initialProducts = mutableStateListOf(
        Product(1, "Chocolat", 1, 2),
        Product(2, "Pomme", 6, 1),
        Product(3, "Saucisson", 1, 2),
        Product(4, "Beurre", 1, 3),
        Product(5, "Farine", 1, 4),
        Product(6, "Lardon", 2, 5),
        Product(7, "Pain", 4, 2),
        Product(8, "Levure boulangère", 1, 3),
        Product(9, "Huile", 2, 4),
        Product(10, "Frites", 1, 5),
        Product(11, "Pâtes", 3, 6),
        Product(12, "Cassoulet", 1, 7),
        Product(13, "Ratatouille", 1, 8),
        Product(14, "Biscuits", 2, 10),
        Product(15, "Madeleines", 1, 8),
        Product(16, "Café", 1, 9),
        Product(17, "Cappuccino", 1, 0),
        Product(18, "Banana", 10, 1),
        Product(19, "Poivre", 1, 3),
        Product(20, "Sauce tomato", 1, 1),
        Product(21, "Mayonnaise", 1, 2)
    )

    val _deletedProducts = mutableStateListOf<Product>()

    val _categories = mutableStateListOf(
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
        Category(11, "Sans catégorie"),
    )
}