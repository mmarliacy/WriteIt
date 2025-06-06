package com.projects.writeit.feature_product.presentation.add_edit_product.old_components

import androidx.compose.runtime.Composable

@Composable
fun CustomDropdownMenu() {
    /*
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

val showBottomSheet by remember {
    mutableStateOf(viewModel.bottomSheetStatus)
}
val categorySelected by remember {
    mutableStateOf(viewModel.categorySelected)
}



Column (
    modifier = modifier.padding(start = 20.dp, end = 20.dp)
){
    OutlinedTextField(
        value = selectedItem,
        onValueChange = { selectedItem = categorySelected.value },
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
                    showBottomSheet.value = true
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
                    selectedItem = it
                    expanded = false
                },
                text = {
                    Text(text = it)
                }
            )
        }
    }
}

 */
}