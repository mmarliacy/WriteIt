package com.projects.writeit.feature_product.presentation.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TabRow
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.writeit.feature_product.presentation.products.components.ModalBottomSheet
import com.projects.writeit.feature_product.presentation.products.components.lists.ArchivedList
import com.projects.writeit.feature_product.presentation.products.components.lists.ShopList
import com.projects.writeit.feature_product.presentation.products.components.tabs.Tabs
import com.projects.writeit.feature_product.presentation.util.DialogEvent
import com.projects.writeit.feature_product.presentation.util.DialogType
import com.projects.writeit.ui.theme.accentColor
import com.projects.writeit.ui.theme.blackColor
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.secondaryText
import com.projects.writeit.ui.theme.whiteColor
import kotlinx.coroutines.launch

@Composable
fun Main(mainViewModel: MainViewModel = viewModel(), modifier: Modifier) {
    val showAddDialog = remember {
        mainViewModel.addDialogStatus
    }
    // Pager
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { Tabs.entries.size })
    val selectedPageIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    val initialListState = rememberLazyListState()

    val expandedFab by remember { derivedStateOf { initialListState.firstVisibleItemIndex != 0 } }
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
                        backgroundColor = blackColor
                    )
                },
                //floatingActionButton
                floatingActionButton = {
                    ExtendedFloatingActionButton(containerColor = Color.Black,
                        contentColor = Color.White,
                        expanded = expandedFab,
                        icon = { Icon(Icons.Filled.Add, "Open Add dialog") },
                        text = { Text(text = "Ajouter") },
                        modifier = Modifier.padding(20.dp),
                        onClick = {
                            showAddDialog.value = true
                        })
                }, floatingActionButtonPosition = FabPosition.End, modifier = modifier.fillMaxSize()
                //ModalBottomSheet
            ) {
                if (showAddDialog.value) {
                    mainViewModel.DialogEvent(
                        dialogEvent = DialogEvent(DialogType.CustomAddDialog, "Bottom Sheet"),
                        mainViewModel,
                        modifier
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding())
                        .background(whiteColor)
                ) {
                    TabRow(
                        selectedTabIndex = selectedPageIndex.value,
                        backgroundColor = whiteColor,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Tabs.entries.forEachIndexed { index, currentTab ->
                            Tab(
                                selected = selectedPageIndex.value == index,
                                selectedContentColor = accentColor,
                                unselectedContentColor = secondaryText,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = {
                                    Text(
                                        text = currentTab.tabName,
                                        fontSize = 16.sp)
                                }
                            )
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier =Modifier.fillMaxWidth().weight(1f)
                    ) {
                        when(pagerState.currentPage){
                            0 -> ShopList(mainViewModel, initialListState)
                            1 -> ArchivedList(mainViewModel)
                        }
                    }
                }
            }
        }
    }
}










