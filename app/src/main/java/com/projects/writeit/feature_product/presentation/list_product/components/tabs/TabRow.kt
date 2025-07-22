package com.projects.writeit.feature_product.presentation.list_product.components.tabs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.util.TabItem
import com.projects.writeit.ui.theme.BlueGrey
import com.projects.writeit.ui.theme.BlueNeutral
import com.projects.writeit.ui.theme.BluePrimary
import com.projects.writeit.ui.theme.White
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
) {
    // -> Position actuelle du Pager, mis à jour par `derivedStateOf`.
    val selectedTabIndex by remember {
        derivedStateOf { pagerState.currentPage }
    }

    val selectedColor = if (selectedTabIndex == 0) {
        BluePrimary // Couleur bleue pour la wishlist
    } else {
        BlueGrey // Couleur bleu clair pour la liste Caddy
    }

    // -> CoroutineScope utilisé pour animer la transition vers un autre onglet au clic.
    val scope = rememberCoroutineScope()

    // -> Barre d’onglets principale synchronisée avec l'HorizontalPager.
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = White,
        modifier = modifier.fillMaxWidth(),
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = selectedColor
            )
        }
    ) {
        // -> Génère dynamiquement chaque onglet à partir des valeurs de l’énumération TabItem.
        Tab(
            // Wish Tab
            selected = selectedTabIndex == 0,
            selectedContentColor = BluePrimary,
            unselectedContentColor = BlueNeutral,
            onClick = {
                // -> Lance une animation pour faire défiler le pager vers la page correspondante.
                scope.launch {
                    pagerState.animateScrollToPage(0)
                }
            },
            // Affiche l’icône définie dans TabItem pour chaque onglet.
            icon = {
                Icon(
                    imageVector = TabItem.WishList.icon,
                    contentDescription = "icon",
                    modifier = Modifier.size(30.dp)
                )
            }
        )
        // CaddyTab
        Tab(
            selected = selectedTabIndex == 1,
            selectedContentColor = BlueGrey,
            unselectedContentColor = BlueNeutral,
            onClick = {
                // -> Lance une animation pour faire défiler le pager vers la page correspondante.
                scope.launch {
                    pagerState.animateScrollToPage(1)
                }
            },
            // Affiche l’icône définie dans TabItem pour chaque onglet.
            icon = {
                Icon(
                    imageVector = TabItem.CaddyList.icon,
                    contentDescription = "icon",
                    modifier = Modifier.size(30.dp)
                )
            }
        )
    }
}
