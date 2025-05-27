package com.projects.writeit.feature_product.presentation.util

import androidx.compose.runtime.mutableStateListOf
import com.projects.writeit.feature_product.domain.model.Product

object Lists {

   val initialProducts: List<Product> = listOf(
      Product(1, "Chocolat", 1, 2.50, "", 2345),
      Product(2, "Pomme", 6, 0.70, "", 2345),
      Product(3, "Saucisson", 1, 3.80, "", 2345),
      Product(4, "Beurre", 1, 1.95, "", 2345),
      Product(5, "Farine", 1, 0.95, "", 2345),
      Product(6, "Lardon", 2, 1.80, "", 2345),
      Product(7, "Pain", 4, 1.10, "", 2345),
      Product(8, "Levure boulangère", 1, 0.60, "", 2345),
      Product(9, "Huile", 2, 1.30, "", 2345),
      Product(10, "Frites", 1, 1.80, "", 2345),
      Product(11, "Pâtes", 3, 0.70, "", 2345),
      Product(12, "Cassoulet", 1, 1.60, "", 2345),
      Product(13, "Ratatouille", 1, 1.40, "", 2345),
      Product(14, "Biscuits", 2, 1.35, "", 2345),
      Product(15, "Madeleines", 1, 1.35, "", 2345),
      Product(16, "Café", 1, 2.10, "", 2345),
      Product(17, "Cappuccino", 1, 1.95, "", 2345),
      Product(18, "Banana", 10, 0.70, "", 2345),
      Product(19, "Poivre", 1, 0.90, "", 2345),
      Product(20, "Sauce tomato", 1, 0.95, "", 2345),
      Product(21, "Mayonnaise", 1, 1.60, "", 2345)
   )

   val deletedProducts: List<Product> = emptyList()
}