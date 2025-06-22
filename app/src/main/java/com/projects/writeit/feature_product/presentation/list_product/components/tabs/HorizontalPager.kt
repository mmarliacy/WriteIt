package com.projects.writeit.feature_product.presentation.list_product.components.tabs
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditProductViewModel
import com.projects.writeit.feature_product.presentation.list_product.ProductsViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ArchivedList
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ShopList

@Composable
fun CustomHorizontalPager(viewModel: ProductsViewModel,
                          editProductViewModel: AddEditProductViewModel,
                          pagerState: PagerState
){

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) {
        when(pagerState.currentPage){
            0 -> ShopList(viewModel,editProductViewModel)
            1 -> ArchivedList(viewModel)
        }
    }
}
