package com.projects.writeit.feature_product.presentation.list_product.components.tabs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.projects.writeit.ui.theme.accentColor
import com.projects.writeit.ui.theme.secondaryText
import com.projects.writeit.ui.theme.whiteColor
import kotlinx.coroutines.launch

@Composable
fun CustomTabRow(
    selectedPageIndex : Int,
    onTabSelected : () -> Unit,
    modifier: Modifier = Modifier
){

    TabRow(
        selectedTabIndex = selectedPageIndex,
        containerColor = whiteColor,
        modifier = modifier.fillMaxWidth()
    ) {
        TabItem.entries.forEachIndexed { index, currentTab ->
            Tab(
                selected = selectedPageIndex == index,
                selectedContentColor = accentColor,
                unselectedContentColor = secondaryText,
                onClick = {
                        onTabSelected()
                },
                icon = {
                    currentTab.icon
                }
            )
        }
    }
}
