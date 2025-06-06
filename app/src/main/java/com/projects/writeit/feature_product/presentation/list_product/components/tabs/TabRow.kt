package com.projects.writeit.feature_product.presentation.list_product.components.tabs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.writeit.ui.theme.darkAccentColor
import com.projects.writeit.ui.theme.secondaryText
import com.projects.writeit.ui.theme.whiteColor
import kotlinx.coroutines.launch

@Composable
fun CustomTabRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier
){

    val selectedPageIndex by remember {
        derivedStateOf { pagerState.currentPage}
    }
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = selectedPageIndex,
        containerColor = whiteColor,
        modifier = modifier.fillMaxWidth()
    ) {
        TabItem.entries.forEachIndexed { index, currentTab ->
            Tab(
                selected = index == selectedPageIndex,
                selectedContentColor = darkAccentColor,
                unselectedContentColor = secondaryText,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    Icon(
                        imageVector = currentTab.icon,
                        contentDescription = "icon",
                        modifier = Modifier.size(30.dp)
                    )

                }
            )
        }
    }
}
