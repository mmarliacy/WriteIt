package com.projects.writeit.feature_product.presentation.product_list.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.projects.writeit.feature_product.presentation.product_list.MainViewModel

@Composable
fun CustomDropdownMenu(viewModel: MainViewModel, modifier: Modifier) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val categoryList = viewModel.categories
    var selectedItem by remember {
        mutableStateOf("")
    }
    var textFiledSize by remember {
        mutableStateOf(Size.Zero)
    }
    val icon = if (expanded) {
        remember { Icons.Filled.KeyboardArrowUp}
    } else {
        remember { Icons.Filled.KeyboardArrowDown}
    }

    Column (
        modifier = modifier.padding(start = 20.dp, end = 20.dp)
    ){
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFiledSize = coordinates.size.toSize()

                },
            label = {
                Text(text = "Catégorie de l'article")
            },
            trailingIcon = {
                Icon(icon, "", Modifier.clickable {
                    expanded = !expanded
                    Log.d("Arrow status", "I'm OK'")
                })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.width(
                with(
                    LocalDensity.current
                ) {
                    textFiledSize.width.toDp()
                }
            )
        ) {
            categoryList.forEach {
                DropdownMenuItem(
                    onClick = {
                        selectedItem = it.name
                        expanded = false
                    },
                    text = {
                        Text(text = it.name)
                    }
                )
            }
        }
    }
}