package com.projects.writeit.feature_product.presentation.list_product.components.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.writeit.feature_product.presentation.list_product.MainViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.item.CaddyItem
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent

/**
 * Composable affichant la liste des produits archivés par l'utilisateur.
 *
 * Pour des performances optimales il est construit avec `LazyColumn` et `itemsIndexed()`.
 *
 * La méthode `AnimatedVisibility()` améliore l'expérience utilisateur, lors de l'apparition/disparition du produit.
 *
 * @param viewModel contient l'état de la liste et les actions utilisateur.
 */
@Composable
fun ArchivedList(viewModel: MainViewModel) {

    // -> Variable contenant l'état des produits archivés.
    val state = viewModel.state.value

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        // -> Méthode qui indexe chaque produit archivé et
        // -> qui permet d'interagir individuellement avec chaque item.
        itemsIndexed(
            items = state.pArchivedItems,
            itemContent = { _, archivedProduct ->
                // -> Le produit rétrécit verticalement en 1 seconde pour un retour à la liste
                // -> des produits actifs et se développe verticalement quand il est rajouté à nouveau.
                AnimatedVisibility(
                    visible = state.isDeletedProductIsVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        // -> Réprésente le produit archivé sur lequel on agit.
                        // -> Lors du clic sur l'icône de restauration, l’événement `DisArchiveProduct`
                        // -> est déclenché pour le réintégrer dans la liste des produits actifs.
                        CaddyItem(
                            item = archivedProduct,
                            onRestoreClick =
                            {
                                viewModel.onEvent(ProductsEvent.DisArchiveProduct(archivedProduct))
                            }
                        )
                    }

                }
            })
    }
}