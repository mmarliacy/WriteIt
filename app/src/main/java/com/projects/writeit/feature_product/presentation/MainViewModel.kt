package com.projects.writeit.feature_product.presentation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.presentation.add_edit_product.old_components.ModalBottomSheet
import com.projects.writeit.feature_product.presentation.util.DialogEvent
import com.projects.writeit.feature_product.presentation.util.DialogType
import com.projects.writeit.feature_product.presentation.util.Lists
import com.projects.writeit.feature_product.presentation.util.Lists.deletedProducts

class MainViewModel{

    val initialProducts: List<Product> = Lists.initialProducts
    val deleteProducts: List<Product> = deletedProducts
    val categories: List<String> = Product.categories

    fun addNewProduct(product: Product) {
        Lists.initialProducts.add(product)
    }

    fun deleteProduct(product: Product) {
        deletedProducts.add(product)
        Lists.initialProducts.remove(product)
    }

    fun cancelDeletion(product: Product) {
        Lists.initialProducts.add(product)
        deletedProducts.remove(product)
    }

    var bottomSheetStatus = mutableStateOf(false)
    var addDialogStatus = mutableStateOf(false)
    val categorySelected = mutableStateOf("")


    @Composable
    fun DialogEvent(dialogEvent: DialogEvent, mainViewModel: MainViewModel, modifier: Modifier) {
        dialogEvent.let { event ->
            when (event.dialogType) {
                DialogType.CustomAddDialog -> {
                    /*
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

                     */
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