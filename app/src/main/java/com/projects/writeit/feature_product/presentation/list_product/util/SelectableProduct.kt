package com.projects.writeit.feature_product.presentation.list_product.util

import com.projects.writeit.feature_product.domain.model.Item

/**
 * Classe représentant un produit avec un état de sélection.
 *
 * Utilisée pour permettre la sélection individuelle ou multiple de produits pour supprimer.
 */
data class SelectableProduct(
    val item: Item,
    val isDeleteChecked: Boolean = false,
)