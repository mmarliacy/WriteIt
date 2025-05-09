package com.projects.writeit.feature_product.presentation.add_edit_product.util

import androidx.compose.ui.focus.FocusState

sealed class AddEditProductEvent{
    data class EnteredTitle(val value: String) : AddEditProductEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditProductEvent()
    data class EnteredQuantity(val value: String) : AddEditProductEvent()
    data class ChangeQuantityFocus(val focusState: FocusState) : AddEditProductEvent()
    data object SaveProduct : AddEditProductEvent()
}