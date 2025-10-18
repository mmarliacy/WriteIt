package com.projects.writeit.feature_product.presentation.add_edit_product.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditViewModel

@Composable
fun DropDownMenuCategory(viewModel: AddEditViewModel, categoryList : List <String>){



    Column (
        modifier = Modifier.fillMaxWidth()

    ){

    }
}
@Preview
@Composable
fun tryItOn(){
    val categoryList = listOf("Samedi", "jeudi")
    val viewModel : AddEditViewModel = hiltViewModel()
    DropDownMenuCategory(viewModel, categoryList)
}