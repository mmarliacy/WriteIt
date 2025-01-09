package com.projects.writeit.feature_product.presentation.product_list.components.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projects.writeit.feature_product.presentation.product_list.MainViewModel
import com.projects.writeit.feature_product.presentation.product_list.components.item.ArchivedProductItem

@Composable
fun ArchivedList(mainViewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = mainViewModel.deleteProducts,
            itemContent = { _, deleteProduct ->
                AnimatedVisibility(
                    visible = !mainViewModel.initialProducts.contains(
                        deleteProduct
                    ),
                    enter = expandVertically(),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        ArchivedProductItem(
                            mainViewModel,
                            deleteProduct
                        )
                    }

                }
            })
    }
}