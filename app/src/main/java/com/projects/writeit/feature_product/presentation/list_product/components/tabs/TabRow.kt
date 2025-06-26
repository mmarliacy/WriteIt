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
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.util.TabItem
import com.projects.writeit.ui.theme.darkAccentColor
import com.projects.writeit.ui.theme.secondaryText
import com.projects.writeit.ui.theme.whiteColor
import kotlinx.coroutines.launch

/**
 * Composable représentant une rangée personnalisée d'onglets liée à un `Horizontal Pager`.
 *
 * Lorsqu’un onglet est cliqué, le pager défile automatiquement vers la page associée avec une animation.
 *
 * @param pagerState Etat du Pager utilisé ici pour l'animer en fonction de l'onglet sélectionné.
 * @param modifier Permet d'ajuster l’apparence depuis l’extérieur si nécessaire.
 */
@Composable
fun CustomTabRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier
){
    // -> Position actuelle du Pager, mis à jour par `derivedStateOf`.
    val selectedPageIndex by remember {
        derivedStateOf { pagerState.currentPage}
    }

    // -> CoroutineScope utilisé pour animer la transition vers un autre onglet au clic.
    val scope = rememberCoroutineScope()

    // -> Barre d’onglets principale synchronisée avec l'HorizontalPager.
    TabRow(
        selectedTabIndex = selectedPageIndex,
        containerColor = whiteColor,
        modifier = modifier.fillMaxWidth()
    ) {
        // -> Génère dynamiquement chaque onglet à partir des valeurs de l’énumération TabItem.
        TabItem.entries.forEachIndexed { index, currentTab ->
            Tab(
                selected = index == selectedPageIndex,
                selectedContentColor = darkAccentColor,
                unselectedContentColor = secondaryText,
                onClick = {
                    // -> Lance une animation pour faire défiler le pager vers la page correspondante.
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                // Affiche l’icône définie dans TabItem pour chaque onglet.
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
