package com.projects.writeit.feature_product.presentation.list_product.components.tabs
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditViewModel
import com.projects.writeit.feature_product.presentation.list_product.MainViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ArchivedList
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ShopList

/**
 * Composable représentant un système d'onglets horizontal avec contenu dynamique.
 *
 * Deux écrans différents s'affiche en fonction de l'onglet sélectionné :
 * - Page 0 : liste des produits actifs (`ShopList`)
 * - Page 1 : liste des produits archivés (`ArchivedList`)
 *
 * Le changement de page est contrôlé par `pagerState`, qui détermine l'onglet actuellement affiché.
 *
 * @param viewModel ViewModel principal qui gère les produits actifs et archivés.
 * @param addEditViewModel ViewModel secondaire pour la logique d'ajout/édition de produits.
 * @param pagerState État du pager, partagé avec l'UI pour garder en mémoire l’onglet actif.
 */
@Composable
fun CustomHorizontalPager(viewModel: MainViewModel,
                          addEditViewModel: AddEditViewModel,
                          pagerState: PagerState
){
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) {
        // -> En fonction de la page active le système s'aligne dynamiquement
        // -> pour affiché le contenu qui lui correspond.
        when(pagerState.currentPage){
            0 -> ShopList(viewModel,addEditViewModel)
            1 -> ArchivedList(viewModel)
        }
    }
}
