package com.projects.writeit.feature_product.presentation.add_edit_product.util

import androidx.compose.ui.focus.FocusState
import com.projects.writeit.feature_product.domain.model.Product

/**
 * Classe scellée représentant tous les événements possibles liés à l'ajout de produit.
 *
 * Ces événements sont utilisés pour déclencher des actions spécifiques dans le dialogue d'ajout de produit.
 *
 * Cela inclut : le focus et la saisie dans un champ de texte,
 * la mise à jour d'un produit, et sa sauvegarde dans la base de données locale.
 */
sealed class AddEditProductEvent{
    data class EnteredName(val value: String) : AddEditProductEvent()
    data class ChangeNameFocus(val focusState: FocusState) : AddEditProductEvent()
    data class EnteredQuantity(val value: String) : AddEditProductEvent()
    data class ChangeQuantityFocus(val focusState: FocusState) : AddEditProductEvent()
    data class EnteredPrice(val value: String) : AddEditProductEvent()
    data class ChangePriceFocus(val focusState: FocusState) : AddEditProductEvent()
    data class GetProductToEdit(val product : Product) : AddEditProductEvent()
    data object SaveProduct : AddEditProductEvent()
}