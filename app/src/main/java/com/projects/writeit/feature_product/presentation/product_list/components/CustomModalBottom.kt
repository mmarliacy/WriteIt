package com.projects.writeit.feature_product.presentation.product_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.writeit.feature_product.domain.model.Category
import com.projects.writeit.feature_product.presentation.product_list.MainViewModel


@Composable
fun CustomModalBottom(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier
) {
    val categoryList = viewModel.categories

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        categoryList.forEach { category ->
            CategoryItem(
                category
            )
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = category.name
        )
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.LightGray
        )
    }

}



