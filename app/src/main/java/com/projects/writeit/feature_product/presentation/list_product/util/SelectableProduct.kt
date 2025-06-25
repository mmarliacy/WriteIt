package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Product

/**
 * Classe représentant un produit avec un état de sélection.
 *
 * Utilisée pour permettre la sélection individuelle ou multiple de produits
 * (par exemple pour les supprimer ou les archiver en masse).
 */
data class SelectableProduct(
    val product: Product,
    val isChecked: Boolean = false
)