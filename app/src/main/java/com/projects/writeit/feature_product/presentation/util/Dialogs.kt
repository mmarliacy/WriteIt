package com.projects.writeit.feature_product.presentation.util

data class DialogEvent(
    val dialogType: DialogType,
    val dialogName: String? = null
)

enum class DialogType{
    CustomAddDialog,
    CustomBottomSheetDialog
}
