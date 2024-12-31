package com.projects.writeit.feature_product.presentation.product_list

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.writeit.feature_product.presentation.product_list.components.ModalBottomSheet
import com.projects.writeit.feature_product.presentation.product_list.components.item.ArchivedProductItem
import com.projects.writeit.feature_product.presentation.product_list.components.item.ProductItem
import com.projects.writeit.feature_product.presentation.util.DialogEvent
import com.projects.writeit.feature_product.presentation.util.DialogType
import com.projects.writeit.ui.theme.latoFamily

@Composable
fun Main(mainViewModel: MainViewModel = viewModel(), modifier: Modifier) {

    val showAddDialog = remember {
        mainViewModel.addDialogStatus
    }
    val listState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex != 0 } }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val showBottomSheet by remember {
        mainViewModel.bottomSheetStatus
    }
    ModalBottomSheetLayout(sheetState = sheetState, sheetContent = {
        if (showBottomSheet) {
            ModalBottomSheet(mainViewModel)
        }
    }) {
        Surface(
            modifier
                .windowInsetsPadding(WindowInsets.systemBars)
                .imePadding() // padding for the bottom for the IME
                .fillMaxSize()
        ) {
            Scaffold(
                //floatingActionButton
                floatingActionButton = {
                    ExtendedFloatingActionButton(containerColor = Color.Black,
                        contentColor = Color.White,
                        expanded = expandedFab,
                        icon = { Icon(Icons.Filled.Add, "Open Add dialog") },
                        text = { Text(text = "Ajouter") },
                        modifier = Modifier.padding(bottom = 120.dp),
                        onClick = {
                            showAddDialog.value = true
                        })
                }, floatingActionButtonPosition = FabPosition.End, modifier = modifier.fillMaxSize()
                //ModalBottomSheet
            ) { innerPadding ->
                if (showAddDialog.value) {
                    mainViewModel.DialogEvent(
                        dialogEvent = DialogEvent(DialogType.CustomAddDialog, "Bottom Sheet"),
                        mainViewModel,
                        modifier
                    )
                }

                LazyColumn(
                    modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    state = listState
                ) {
                    item {
                        MainList(mainViewModel)
                    }
                    item {
                        Column(Modifier.fillMaxWidth()) {
                            HorizontalDivider(thickness = 0.5.dp)
                            Text(
                                text = "Articles archivés",
                                fontSize = 20.sp,
                                color = Color.DarkGray,
                                fontFamily = latoFamily,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    top = 10.dp,
                                    bottom = 10.dp
                                )
                            )
                        }
                    }
                    archivedList(mainViewModel)

                }
            }
        }
    }
}

@Composable
fun MainList(mainViewModel: MainViewModel) {
    val productList = remember {
        mainViewModel.initialProducts
    }

    LazyColumn(
        modifier = Modifier.height(800.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
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

fun LazyListScope.archivedList(mainViewModel: MainViewModel) {
    itemsIndexed(items = mainViewModel.deleteProducts, itemContent = { _, deleteProduct ->
        AnimatedVisibility(
            visible = !mainViewModel.initialProducts.contains(
                deleteProduct
            ),
            enter = expandVertically(),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
        ) {
            ArchivedProductItem(mainViewModel, deleteProduct)
        }
    })
}

