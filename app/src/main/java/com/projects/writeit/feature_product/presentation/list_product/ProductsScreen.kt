package com.projects.writeit.feature_product.presentation.list_product

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.projects.writeit.feature_product.presentation.list_product.components.CustomHorizontalPager
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ShopList
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.CustomTabRow
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.TabItem
import com.projects.writeit.feature_product.presentation.util.Screen
import com.projects.writeit.ui.theme.blackColor
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.whiteColor
import kotlinx.coroutines.launch

@Composable
fun ProductsScreen(
    navController: NavController,
    viewModel: ProductsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state
    val pagerState = rememberPagerState(pageCount = { TabItem.entries.size })
    val scope = rememberCoroutineScope()
    val selectedPageIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    val initialListState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { initialListState.firstVisibleItemIndex != 0 } }

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
                        navController.navigate(Screen.AddEditProductScreen)
                    })
            }, floatingActionButtonPosition = FabPosition.End, modifier = modifier.fillMaxSize()
            //ModalBottomSheet
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
                    .background(whiteColor)
            ) {
                CustomTabRow(
                    selectedPageIndex = selectedPageIndex.value,
                    onTabSelected = {
                        scope.launch {
                            pagerState.animateScrollToPage(selectedPageIndex.value)
                        }
                    }
                )

                CustomHorizontalPager(
                    viewModel,
                    scaffoldState
                )

                ShopList(
                    viewModel = viewModel,
                    scaffoldState = scaffoldState
                )

            }
        }
    }
}











