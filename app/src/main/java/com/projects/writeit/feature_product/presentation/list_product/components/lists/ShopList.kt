package com.projects.writeit.feature_product.presentation.list_product.components.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent
import com.projects.writeit.feature_product.presentation.list_product.ProductsViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.item.ProductItem
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
            AnimatedVisibility(
                visible = !state.isDeletedProductIsVisible,
                enter = expandVertically(),
                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
            ) {
                ProductItem(
                    product = product,
                    onDeleteClick = {
                        viewModel.onEvent(ProductsEvent.DeleteProduct(product))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Produit supprimé",
                                actionLabel = "Annuler"
                            )
                            if(result == SnackbarResult.ActionPerformed){
                                viewModel.onEvent(ProductsEvent.RestoreProduct)
                            }
                        }

                    }
                )
            }
        }
    }
}