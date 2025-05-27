package com.projects.writeit.feature_product.presentation.list_product.components.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.PagerState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent
import com.projects.writeit.feature_product.presentation.list_product.ProductsViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.item.ProductItem
import com.projects.writeit.ui.theme.PurpleGrey40
import kotlinx.coroutines.launch

@Composable
fun ShopList(
    viewModel: ProductsViewModel,
    scaffoldState: ScaffoldState
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(state.products) { _, product ->
            ProductItem(
                product = product,
                onDeleteClick = {
                    viewModel.onEvent(ProductsEvent.DeleteProduct(product))
                    scope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = "Produit supprimé",
                            actionLabel = "Annuler"
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(ProductsEvent.RestoreProduct)
                        }
                    }

                }
            )
        }
    }
}