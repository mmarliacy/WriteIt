package com.projects.writeit.feature_product.presentation.list_product.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.TabItem
import com.projects.writeit.ui.theme.accentColor
import com.projects.writeit.ui.theme.secondaryText
import com.projects.writeit.ui.theme.whiteColor

@Composable
fun Tabs (){
    /*
    TabRow(
        selectedTabIndex = selectedPageIndex.value,
        containerColor = whiteColor,
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        TabItem.entries.forEachIndexed { index, currentTab ->
            Tab(
                selected = selectedPageIndex.value == index,
                selectedContentColor = accentColor,
                unselectedContentColor = secondaryText,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(text = currentTab.tabName)
                }
            )
        }
    }

     */
}
