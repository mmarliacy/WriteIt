package com.projects.writeit.feature_product.presentation.product_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.presentation.product_list.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalBottom(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier,
    showBottomSheet: Boolean
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var productName by remember {
        mutableStateOf("")
    }
    var productQuantity by remember {
        mutableStateOf("")
    }
    var showSheet by remember { mutableStateOf(true) }

    var expanded by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()

    val categoryList = viewModel.categories

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {
        //Category
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Column (
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = AbsoluteAlignment.Left,
                modifier = Modifier.background(Color.Black)
            ){
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = {
                        Text(
                            text = "Nom de l'article"
                        )
                    },
                    textStyle = TextStyle(
                        fontStyle = FontStyle.Normal,
                        color = Color.Black
                    )
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    scrollState = scrollState,
                    modifier = Modifier.background(Color.Red)
                ) {
                    repeat(30) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Item : ${it + 1}"
                                )
                            },
                            onClick = {
                            },
                        )

                    }
                }
                LaunchedEffect(expanded) {
                    if (expanded) {
                        // Scroll to show the bottom menu items.
                        scrollState.scrollTo(scrollState.maxValue)
                    }
                }
            }
            OutlinedTextField(
                value = productQuantity,
                label = {
                    Text(
                        text = "Quantité"
                    )
                },
                onValueChange = { productQuantity = it },
                textStyle = TextStyle(
                    fontStyle = FontStyle.Normal,
                    color = Color.Black
                )
            )
        }


    }
    Button(
        onClick = {
            viewModel.addNewProduct(Product(null, productName, null, null))
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showSheet = false
                }
            }
            productName = ""
        }
    ) {
        Text(
            text = "OK",
        )
    }
}

