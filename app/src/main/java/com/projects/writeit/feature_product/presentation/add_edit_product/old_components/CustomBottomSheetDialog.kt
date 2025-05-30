package com.projects.writeit.feature_product.presentation.add_edit_product.old_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.writeit.feature_product.presentation.MainViewModel
import com.projects.writeit.ui.theme.latoFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(mainViewModel: MainViewModel){
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

    val categorySelected by remember {
        mutableStateOf(viewModel.categorySelected)
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        categoryList.forEach { category ->
            CategoryItem(
                category,
                onClick =
                {
                    categorySelected.value = category
                }
            )
        }
    }
}

@Composable
fun CategoryItem(category: String, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.clickable {
            onClick()
        }
    ){
        Text(
            text = category,
            fontFamily = latoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier.padding(
                top = 15.dp,
                bottom = 15.dp,
                start = 10.dp,
                end = 10.dp
            )
        )
    }
}



