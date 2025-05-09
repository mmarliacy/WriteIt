package com.projects.writeit.feature_product.presentation.list_product.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projects.writeit.feature_product.presentation.list_product.ProductsViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.TabItem
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ArchivedList
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ShopList

@Composable
fun CustomHorizontalPager(viewModel: ProductsViewModel,
                          scaffoldState: ScaffoldState
){

    val pagerState = rememberPagerState(pageCount = { TabItem.entries.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) {
        when(pagerState.currentPage){
            0 -> ShopList(viewModel, scaffoldState)
            1 -> ArchivedList(viewModel)
        }
    }
}
