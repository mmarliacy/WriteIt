package com.projects.writeit.feature_product.domain.util

/**
 * Classe scellée représentant les critères de tri disponibles pour les produits.
 *
 * Chaque critère (nom, date de création, catégorie) est associé à un type d'ordre
 * (ascendant ou descendant), défini par la classe OrderType.
 *
 * Permet à l'utilisateur de personnaliser l'affichage des produits dans l'UI.
 */
sealed class ItemOrder(val orderType: OrderType){
    class Name (orderType: OrderType): ItemOrder(orderType)
    class Date (orderType: OrderType): ItemOrder(orderType)
    class Category (orderType: OrderType): ItemOrder(orderType)
}