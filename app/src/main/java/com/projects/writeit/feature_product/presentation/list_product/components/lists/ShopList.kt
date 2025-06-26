package com.projects.writeit.feature_product.presentation.list_product.components.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.util.AddEditProductEvent
import com.projects.writeit.feature_product.presentation.list_product.ProductsViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.item.ProductItem
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent

@Composable
fun ShopList(
    viewModel: ProductsViewModel,
    editViewModel: AddEditViewModel
) {
    val state = viewModel.state.value
    val haptics = LocalHapticFeedback.current

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(state.selectableActiveProducts) { _, selectableProduct ->
            AnimatedVisibility(
                visible = state.isDeletedProductIsVisible,
                enter = expandVertically(),
                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
            ) {
                ProductItem(
                    product = selectableProduct.product,
                    isDeletionModeActive = state.isSelectionMode,
                    onClickItem = {
                        viewModel.onEvent(ProductsEvent.ArchiveProduct(product = selectableProduct.product))
                    },
                    onLongClickItem = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        viewModel.onEvent(ProductsEvent.ToggleBottomDialog)
                        selectableProduct.product.id?.let{
                            if (it != viewModel.productToEdit.value?.id){
                                viewModel.productToEdit(product = selectableProduct.product)
                                editViewModel.onEvent(AddEditProductEvent.InitProduct(
                                    product = selectableProduct.product
                                ))
                            }

                    }
                    },

                    onCheckedChange = { isChecked ->
                        viewModel.onEvent(
                            ProductsEvent.ToggleProductSelection(
                                productId = selectableProduct.product.id!!,
                                isChecked = isChecked
                                )
                        )
                    },
                    checked = selectableProduct.isChecked
                )
            }
        }
    }
}