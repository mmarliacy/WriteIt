package com.projects.writeit.feature_product.domain.util

/**
 * Classe scellée représentant les types d'ordonnancement disponibles pour le tri des produits.
 *
 * Utilisée pour définir si le tri est effectué en ordre ascendant ou descendant,
 */
sealed class OrderType {
    data object AscendingOrder : OrderType()
    data object DescendingOrder : OrderType()
}