package com.projects.writeit.feature_product.presentation.product_list


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.projects.writeit.feature_product.domain.model.Category
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.presentation.product_list.components.CustomAddContent
import com.projects.writeit.feature_product.presentation.product_list.components.ModalBottomSheet
import com.projects.writeit.feature_product.presentation.util.DialogEvent
import com.projects.writeit.feature_product.presentation.util.DialogType
import com.projects.writeit.feature_product.presentation.util.Lists._categories
import com.projects.writeit.feature_product.presentation.util.Lists._deletedProducts
import com.projects.writeit.feature_product.presentation.util.Lists._initialProducts

class MainViewModel : ViewModel() {

    val initialProducts: List<Product> = _initialProducts
    val deleteProducts: List<Product> = _deletedProducts
    val categories: List<Category> = _categories

    fun addNewProduct(product: Product) {
        _initialProducts.add(product)
    }

    fun deleteProduct(product: Product) {
        _deletedProducts.add(product)
        _initialProducts.remove(product)
    }

    fun cancelDeletion(product: Product) {
        _initialProducts.add(product)
        _deletedProducts.remove(product)
    }

    var bottomSheetStatus = mutableStateOf(false)
    var addDialogStatus = mutableStateOf(false)


    @Composable
    fun DialogEvent(dialogEvent: DialogEvent, mainViewModel: MainViewModel, modifier: Modifier) {
        dialogEvent.let { event ->
            when (event.dialogType) {
                DialogType.CustomAddDialog -> {
                    CustomAddContent(
                        onDismissRequest = {
                            addDialogStatus.value = false
                        },
                        onConfirmation = {
                            addDialogStatus.value = false
                        },
                        viewModel = mainViewModel,
                        modifier = modifier
                    )
                }

                DialogType.CustomBottomSheetDialog -> {
                    ModalBottomSheet(
                        mainViewModel = mainViewModel
                    )
                }
            }
        }

    }
}