package com.projects.writeit.feature_product.presentation.list_product.components.top_app_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SortDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismiss: () -> Unit
) {
    Box(
        modifier = modifier.padding(16.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss
        ) {
            DropdownMenuItem(
                text = { Text("Par titre") },
                onClick = {},
                enabled = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Title,
                        contentDescription = "SortByTitle"
                    ) }
            )
            DropdownMenuItem(
                text = { Text("Par Date") },
                onClick = {},
                enabled = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "SortByDate"
                    ) }
            )
            DropdownMenuItem(
                text = { Text("Par catégorie") },
                onClick = {},
                enabled = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Category,
                        contentDescription = "SortByCategory"
                    ) }
            )

        }
    }
}