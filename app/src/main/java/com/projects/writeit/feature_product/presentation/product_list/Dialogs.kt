package com.projects.writeit.feature_product.presentation.product_list

data class DialogEvent(
    val dialogType: DialogType,
    val dialogName: String? = null
)

enum class DialogType{
    CustomAddDialog,
    CustomBottomSheetDialog
}
