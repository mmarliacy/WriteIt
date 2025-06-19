package com.projects.writeit.feature_product.presentation.list_product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EuroSymbol
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditProductViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.BottomAddEditDialog
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ShopList
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.CustomHorizontalPager
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.CustomTabRow
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.TabItem
import com.projects.writeit.feature_product.presentation.list_product.components.top_app_bar.SortDropDownMenu
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent
import com.projects.writeit.ui.theme.blackColor
import com.projects.writeit.ui.theme.darkAccentColor
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.whiteColor
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    editViewModel: AddEditProductViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    // val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(pageCount = { TabItem.entries.size })
    val currentSum by viewModel.totalPriceSum.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var dropDownExpanded by remember {
        mutableStateOf(false)
    }
    val state = viewModel.state.value
    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(
        true
    ) {
        editViewModel.eventFlow.collectLatest { event ->
            if (event is AddEditProductViewModel.UiEvent.ShowSnackBar) {
                snackBarHostState.showSnackbar(event.message)
            }
        }
    }

    Surface(
        modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .imePadding() // padding for the bottom for the IME
            .fillMaxSize()
    ) {
        Scaffold(
            snackbarHost = {SnackbarHost(hostState = snackBarHostState)},
                    topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Ma liste de courses",
                            color = whiteColor,
                            fontFamily = latoFamily,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )
                    },
                    colors = TopAppBarColors(
                        containerColor = blackColor,
                        scrolledContainerColor = blackColor,
                        navigationIconContentColor = whiteColor,
                        titleContentColor = whiteColor,
                        actionIconContentColor = whiteColor

                    ),
                    actions = {
                        if (state.buttonDeleteIsVisible) {
                            TextButton(
                                onClick = {
                                    viewModel.onEvent(ProductsEvent.DeleteSelectedProducts(state.selectableActiveProducts))
                                }
                            ) {
                                Text(
                                    text = "Supprimer",
                                    modifier = Modifier.padding(end = 20.dp),
                                    color = Color.White
                                )
                            }
                        }
                        Text(
                            text = "$currentSum €",
                            modifier = Modifier.padding(end = 20.dp),
                            color = Color.White
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = {
                            dropDownExpanded = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = "SortButton"
                            )
                            SortDropDownMenu(
                                expanded = dropDownExpanded,
                                onDismiss = {
                                    dropDownExpanded = false
                                }
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.EuroSymbol,
                                contentDescription = "BudgetButton"
                            )
                        }
                        IconButton(onClick = {
                            viewModel.onEvent(ProductsEvent.ToggleProductSelectionMode)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "DeleteButton"
                            )
                        }
                    },
                    //floatingActionButton
                    floatingActionButton = {
                        when (pagerState.currentPage) {
                            0 -> {
                                FloatingActionButton(containerColor = Color.Black,
                                    contentColor = Color.White,
                                    onClick = {
                                        showBottomSheet = true
                                    }) {
                                    Icon(Icons.Filled.Add, "Open Add dialog")
                                }
                            }

                            1 -> {
                                FloatingActionButton(containerColor = darkAccentColor,
                                    contentColor = Color.White,
                                    onClick = {
                                        viewModel.onEvent(ProductsEvent.RestoreAllProducts)
                                    }) {
                                    Icon(Icons.Filled.Restore, "Restore All")
                                }
                            }
                        }
                    }
                )
            },

            floatingActionButtonPosition = FabPosition.End,
            modifier = modifier.fillMaxSize(),


            //ModalBottomSheet
        ) {
            if (showBottomSheet) {
                BottomAddEditDialog(
                    onDismiss = { showBottomSheet = false },
                    modifier = Modifier
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                    .background(whiteColor)
            ) {
                CustomTabRow(pagerState = pagerState)

                CustomHorizontalPager(
                    viewModel,
                    pagerState
                )

                ShopList(
                    viewModel = viewModel
                )
            }
        }
    }
}











