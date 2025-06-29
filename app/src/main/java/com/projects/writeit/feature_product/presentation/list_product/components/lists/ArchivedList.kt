package com.projects.writeit.feature_product.presentation.list_product.components.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.writeit.feature_product.presentation.list_product.ProductsViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.item.ArchivedProductItem
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent

@Composable
fun ArchivedList(viewModel: ProductsViewModel) {
    val state = viewModel.state.value
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(
            items = state.archivedProducts,
            itemContent = { _, archivedProduct ->
                AnimatedVisibility(
                    visible = state.isDeletedProductIsVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        ArchivedProductItem(
                            product = archivedProduct,
                            onRestoreClick =
                            {
                                viewModel.onEvent(ProductsEvent.DisArchiveProduct(archivedProduct))
                            }
                        )
                    }

                }
            })
    }
}