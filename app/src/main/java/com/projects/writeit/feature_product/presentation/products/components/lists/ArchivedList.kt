package com.projects.writeit.feature_product.presentation.products.components.lists

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
import com.projects.writeit.feature_product.presentation.products.MainViewModel
import com.projects.writeit.feature_product.presentation.products.components.item.ArchivedProductItem

@Composable
fun ArchivedList(mainViewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
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