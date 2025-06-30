package com.projects.writeit.feature_product.domain.util

/**
 * Classe de données représentant l'état d'un champ de texte personnalisé.
 *
 * Permet de stocker et de manipuler le contenu d'un champ de saisie,
 * ainsi que ses éventuelles erreurs de validation.
 */
data class ProductTextFieldState (
    val hint: String = "",
    val nameText: String = "",
    val quantityText: String = "",
    val priceText : String = "",
    val quantity : Int = 0,
    val price : Double = 0.0,
    val isHintVisible: Boolean = true
)
