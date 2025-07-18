package com.projects.writeit.feature_product.presentation.list_product.components.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.util.AddEditItemEvent
import com.projects.writeit.feature_product.presentation.list_product.MainViewModel
import com.projects.writeit.feature_product.presentation.list_product.components.item.WishItem
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent

/**
 * Composable affichant la liste des produits actifs par l'utilisateur.
 *
 * Construit avec `LazyColumn` et `itemsIndexed()`, il permet
 * d'afficher, sélectionner, modifier ou archiver un produit.
 *
 * La méthode `AnimatedVisibility()` fluidifie les transitions, lors de l'apparition/disparition du produit.
 *
 * @param viewModel Gère l’état global des produits actifs et les actions utilisateur.
 * @param editViewModel utilisé lors de la mise à jour d'un produit.
 */
@Composable
fun ShopList(
    viewModel: MainViewModel,
    editViewModel: AddEditViewModel
) {
    // -> Variable contenant l'état des produits actifs.
    val state = viewModel.state.value

    // -> Retour haptique (concerne le sens du toucher) pour les interactions longues (ex. : long press).
    val haptics = LocalHapticFeedback.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        // -> Affiche chaque produit actif avec son index.
        // -- Permet d’interagir individuellement avec chaque élément.
        itemsIndexed(state.selectableActiveProducts) { _, selectableProduct ->
            // -> Anime l’apparition ou disparition de l’item :
            //  - ExpandVertically → lors du retour dans la liste
            //  - ShrinkVertically → lors de l’archivage (animation de 1 seconde)
            AnimatedVisibility(
                visible = state.isDeletedProductIsVisible,
                enter = expandVertically(),
                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
            ) {

                //---------------------------------------------------------------------------------------
                // -> LE PRODUIT (L'ITEM) --> Réprésente le produit actif.
                // ----------------------------------
                // -> `isDeletionModeActive` → active l’affichage des cases à cocher si le mode suppression est lancé.
                // -> `onClickItem` → Au clic simple sur l'item, le produit est archivé et déplacé dans la liste des archivés.
                // -> `OnLongClickItem` → Le dialogue d'édition s'affiche par la méthode `ToggleBottomDialog`
                //      → s'il s'agit d'un produit existant alors l'évènement `InitProduct` liée à `EditViewModel`
                //      → récupère et remplit d'avance le formulaire avec les valeurs connues du produit.
                // -> `OnCheckedChange → déclenche ou annule la sélection de l'item pour suppression multiple.
                // -> `OnChecked` → Indique si l'item est actuellement sélectionné.
                //------------------------------------
                WishItem(
                    item = selectableProduct.pItem,
                    isDeletionModeActive = state.isSelectionMode,
                    putInCaddyClick = {
                        viewModel.onEvent(ProductsEvent.ArchiveProduct(pItem = selectableProduct.pItem))
                    },
                    onClickItem = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        viewModel.onEvent(ProductsEvent.ToggleBottomDialog)
                        selectableProduct.pItem.id?.let {
                            if (it != viewModel.fItemToEdit.value?.id) {
                                viewModel.productToEdit(pItem = selectableProduct.pItem)
                                editViewModel.onEvent(
                                    AddEditItemEvent.GetProductToEdit(
                                        pItem = selectableProduct.pItem
                                    )
                                )
                            }
                        }
                    },

                    onCheckedChange = { isChecked ->
                        viewModel.onEvent(
                            ProductsEvent.ToggleProductSelection(
                                productId = selectableProduct.pItem.id!!,
                                isChecked = isChecked
                            )
                        )
                    },
                    checked = selectableProduct.isChecked
                )
            }
        }
    }
}