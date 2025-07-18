package com.projects.writeit.feature_product.domain.util

/**
 * Classe de données représentant l'état d'un champ de texte personnalisé.
 *
 * Permet de stocker et de manipuler le contenu d'un champ de saisie,
 * ainsi que ses éventuelles erreurs de validation.
 */
data class ItemTextFieldState (
    val hint: String = "",
    val nameText: String = "",
    val quantityText: String = "",
    val priceText : String = "",
    val isHintVisible: Boolean = true,
    val isError: Boolean = false,
    val supportingText: String? = null
)
