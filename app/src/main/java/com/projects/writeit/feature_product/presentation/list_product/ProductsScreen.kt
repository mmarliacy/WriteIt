package com.projects.writeit.feature_product.presentation.list_product

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EuroSymbol
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
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
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    editViewModel: AddEditProductViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(pageCount = { TabItem.entries.size })
    val currentSum by viewModel.totalPriceSum.collectAsState()

    var dropDownExpanded by remember {
        mutableStateOf(false)
    }
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val viewModelScope = rememberCoroutineScope()


    LaunchedEffect(
        true
    ) {
        launch{
            editViewModel.eventFlow.collectLatest { event ->
                if (event is AddEditProductViewModel.UiEvent.ShowSnackBar) {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
        launch {
            viewModel.eventFlow.collectLatest { event ->
                if (event is ProductsViewModel.UiEvent.ShowSnackBar) {
                    Log.d("SNACKBAR_EVENT", "Message reçu : ${event.message}")
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
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
                    IconButton(onClick = {
                        Log.d("SNACKBAR_EVENT", "Clic sur bouton test")
                        viewModelScope.launch {
                            viewModel.eventFlow.collectLatest {
                                Log.d("SNACKBAR_EVENT", "Événement reçu en direct depuis bouton")
                            }
                        }

                        // Appelle directement le snackbar
                        viewModelScope.launch {
                            viewModel._eventFlow.emit(ProductsViewModel.UiEvent.ShowSnackBar("Clic détecté"))
                        }
                    }) {
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
                                    editViewModel.prepareForNewProduct()
                                    viewModel.onEvent(ProductsEvent.ToggleBottomDialog)
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

        if (state.showBottomSheet) {
            BottomAddEditDialog(
                onDismiss = {
                    viewModel.onEvent(ProductsEvent.ToggleBottomDialog)
                            },
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .background(whiteColor)
        ) {
            CustomTabRow(pagerState = pagerState)

            CustomHorizontalPager(
                viewModel,
                editViewModel,
                pagerState
            )

            ShopList(
                viewModel = viewModel,
                editViewModel = editViewModel
            )
        }
    }
}











