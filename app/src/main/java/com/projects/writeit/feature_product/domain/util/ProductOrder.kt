package com.projects.writeit.feature_product.domain.util

/**
 * Classe scellée représentant les critères de tri disponibles pour les produits.
 *
 * Chaque critère (nom, date de création, catégorie) est associé à un type d'ordre
 * (ascendant ou descendant), défini par la classe OrderType.
 *
 * Permet à l'utilisateur de personnaliser l'affichage des produits dans l'UI.
 */
sealed class ProductOrder(val orderType: OrderType){
    class Name (orderType: OrderType): ProductOrder(orderType)
    class Date (orderType: OrderType): ProductOrder(orderType)
    class Category (orderType: OrderType): ProductOrder(orderType)
}