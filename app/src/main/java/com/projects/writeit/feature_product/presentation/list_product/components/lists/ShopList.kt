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
import androidx.compose.ui.unit.dp
import com.projects.writeit.feature_product.presentation.list_product.ProductsViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.item.ProductItem
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent

@Composable
fun ShopList(
    viewModel: ProductsViewModel
) {
    val state = viewModel.state.value

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