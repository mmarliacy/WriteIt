package com.projects.writeit.feature_product.presentation.product_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.writeit.feature_product.domain.model.Category
import com.projects.writeit.feature_product.presentation.product_list.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetContent(mainViewModel: MainViewModel){
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet by remember {
        mutableStateOf(mainViewModel.bottomSheetStatus)
    }
    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState
    ) {
        CustomBottomSheetContent(
            mainViewModel,
            Modifier
        )
    }
}


@Composable
fun CustomBottomSheetContent(
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



