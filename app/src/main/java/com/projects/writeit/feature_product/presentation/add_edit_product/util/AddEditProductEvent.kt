package com.projects.writeit.feature_product.presentation.add_edit_product.util

import androidx.compose.ui.focus.FocusState
import com.projects.writeit.feature_product.domain.model.Product

sealed class AddEditProductEvent{
    data class EnteredTitle(val value: String) : AddEditProductEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditProductEvent()
    data class EnteredQuantity(val value: String) : AddEditProductEvent()
    data class ChangeQuantityFocus(val focusState: FocusState) : AddEditProductEvent()
    data class EnteredPrice(val value: String) : AddEditProductEvent()
    data class ChangePriceFocus(val focusState: FocusState) : AddEditProductEvent()
    data class InitProduct(val product : Product) : AddEditProductEvent()
    data object SaveProduct : AddEditProductEvent()
}