package com.projects.writeit.feature_product.domain.use_case

import com.projects.writeit.feature_product.domain.use_case.single_use_case.InsertItem
import com.projects.writeit.feature_product.domain.use_case.single_use_case.DeleteItem
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetCaddyList
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetItem
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetWishList

/**
 * Classe de regroupement des use cases liés à la gestion des produits.
 *
 * Fournit un accès centralisé à toutes les actions possibles :
 * récupération des produits actifs ou archivés, ajout, suppression, et récupération individuelle.
 */
data class ProductUseCases(
    val pGetWishList: GetWishList,
    val pGetCaddyList: GetCaddyList,
    val pDeleteItem: DeleteItem,
    val pInsertItem: InsertItem,
    val pGetItem: GetItem
)