package com.projects.writeit.feature_product.domain.use_case

import com.projects.writeit.feature_product.domain.use_case.single_use_case.AddProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.DeleteProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetArchivedProducts
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProducts

/**
 * Classe de regroupement des use cases liés à la gestion des produits.
 *
 * Fournit un accès centralisé à toutes les actions possibles :
 * récupération des produits actifs ou archivés, ajout, suppression, et récupération individuelle.
 */
data class ProductUseCases(
    val getProducts: GetProducts,
    val getArchivedProducts: GetArchivedProducts,
    val deleteProduct: DeleteProduct,
    val addProduct: AddProduct,
    val getProduct: GetProduct
)