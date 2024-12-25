package com.projects.writeit.feature_product.presentation.product_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.writeit.feature_product.presentation.product_list.components.ArchivedProductItem
import com.projects.writeit.feature_product.presentation.product_list.components.CustomAddDialog
import com.projects.writeit.feature_product.presentation.product_list.components.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(mainViewModel: MainViewModel = viewModel(), modifier: Modifier) {
    val sheetState = rememberModalBottomSheetState()

    var showAddDialog by remember {
        mutableStateOf(false)
    }

    val productList = remember {
        mainViewModel.initialProducts
    }
    val listState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex != 0 } }

    Scaffold(
        //floatingActionButton
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = Color.Black,
                contentColor = Color.White,
                expanded = expandedFab,
                icon = { Icon(Icons.Filled.Add, "Open Add dialog") },
                text = { Text(text = "Ajouter") },
                modifier = Modifier.padding(bottom = 100.dp),
                onClick = {
                    showAddDialog = true
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier.fillMaxSize()
        //ModalBottomSheet
    ) { innerPadding ->
        if(showAddDialog) {
            CustomAddDialog(
                onDismissRequest = { showAddDialog = false },
                onConfirmation = {
                    showAddDialog = false
                },
                viewModel = mainViewModel,
                modifier = modifier
            )
/*
        if(showBottomSheet){
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    CustomModalBottom(mainViewModel, Modifier)
                    mainViewModel.OpenSheet()
                }
        }
 */

        }
        Column(
            modifier
                .windowInsetsPadding(WindowInsets.systemBars)
                .imePadding() // padding for the bottom for the IME
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                LazyColumn(state = listState) {
                    itemsIndexed(productList) { _, product ->
                        AnimatedVisibility(
                            visible = !mainViewModel.deleteProducts.contains(product),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                        ) {
                            ProductItem(mainViewModel, product)
                        }
                    }
                }

            }
            HorizontalDivider(thickness = 2.dp)
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                LazyRow {
                    itemsIndexed(
                        items = mainViewModel.deleteProducts,
                        itemContent = { _, deleteProduct ->
                            AnimatedVisibility(
                                visible = !mainViewModel.initialProducts.contains(deleteProduct),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                            ) {
                                ArchivedProductItem(mainViewModel, deleteProduct)
                            }
                        })
                }
            }

        }
    }
}

