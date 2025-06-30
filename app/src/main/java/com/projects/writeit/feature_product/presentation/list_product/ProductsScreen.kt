package com.projects.writeit.feature_product.presentation.list_product

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EuroSymbol
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditDialog
import com.projects.writeit.feature_product.presentation.list_product.components.lists.ShopList
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.CustomHorizontalPager
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.CustomTabRow
import com.projects.writeit.feature_product.presentation.list_product.components.tabs.util.TabItem
import com.projects.writeit.feature_product.presentation.list_product.components.bottom_app_bar.SortDropDownMenu
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent
import com.projects.writeit.ui.theme.blackColor
import com.projects.writeit.ui.theme.darkAccentColor
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.whiteColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Composable représentant l'écran principal, structuré autour d'un `Scaffold`.
 *
 * Il inclut :
 * - La **liste des produits actifs**, via `ShopList`.
 * - Une **barre supérieure (`TopAppBar`)** contenant :
 *      → le titre
 *      → le montant total des produits
 *      → Le bouton "Supprimer" (affiché uniquement si la sélection est active)
 * - Une **barre inférieure (`BottomAppBar`)** contenant :
 *      → 1 bouton de tri : Par date, par nom, ou par catégorie.
 *      → 1 bouton pour la budgétisation : Qui evoluera en fonction du coût total.
 *      → 1 bouton pour l'activation de suppression multiple.
 * - Une **rangée d’onglets (`TabRow`) synchronisée avec un `HorizontalPager`**
 *     → L’utilisateur peut naviguer entre la liste des produits actifs et la liste des archivés
 *
 * Un **FloatingActionButton** permet :
 * - D’ajouter un nouveau produit (onglet 0), en ouvrant un dialogue bas (`ModalBottomSheet`)
 * - De restaurer tous les produits archivés (onglet 1)
 *
 * Ce composable reçoit également des évènements uniques (Affichage de snack bar...) des deux `Views Models`
 * `ProductViewModel` et `EditViewModel` géré par un `Launched Effect`, suite à l'ajout d'un produit par exemple .
 * Cette gestion d'évènements sur l'écran est géré par le `Scaffold State` (Comme l'affichage du snack bar)
 *
 * @param viewModel gère l'état des produits, et donne accès aux évènements et actions utilisateur.
 * @param editViewModel gère les évènements liés à l'ajout ou l'édition d'un produit.
 * @param modifier Afin d'effectuer des modifications extérieures au Composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    editViewModel: AddEditViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    // → État du pager contrôlé dynamiquement selon le nombre d’onglets définis dans TabItem.
    val pagerState = rememberPagerState(pageCount = { TabItem.entries.size })

    // → Il s'agit du montant total des produits constamment observé via State Flow et collectAsState().
    val currentSum by viewModel.totalPriceSum.collectAsState()

    // -> État complet de l’écran, exposé par le ProductsViewModel.
    val state = viewModel.state.value

    // -> Etat du conteneur de l'écran.
    val scaffoldState = rememberScaffoldState()


    //---------------------------------------------------------------------------------------
    // -> Effet lancé une seule fois lors de la 1ère composition.
    // -> Il observe les flux d’événements des deux ViewModels et affiche des snackbars en conséquence.
    //------------------------------------
    LaunchedEffect(
        true
    ) {
        launch {
            editViewModel.eventFlow.collectLatest { event ->
                if (event is AddEditViewModel.UiEvent.ShowSnackBar) {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
        launch {
            viewModel.eventFlow.collectLatest { event ->
                if (event is ProductsViewModel.UiEvent.ShowSnackBar) {
                    Log.d("SNACKBAR_EVENT", "Message reçu : ${event.message}")
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    // Scaffold principal de l’écran : gère l'agencement général (topBar, bottomBar, FAB, contenu).
    // Il intègre également un snackbarHost pour l'affichage des messages utilisateur.
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
        // Barre supérieure de l’écran contenant le titre,
        // le montant total et le bouton "Supprimer" si une sélection est active.
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ma liste de courses",
                        color = whiteColor,
                        fontFamily = latoFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                },
                colors = TopAppBarColors(
                    containerColor = blackColor,
                    scrolledContainerColor = blackColor,
                    navigationIconContentColor = whiteColor,
                    titleContentColor = whiteColor,
                    actionIconContentColor = whiteColor

                ),
                // Actions situées à droite du titre :
                // - Affichage du montant total des produits
                // - Bouton "Supprimer" visible uniquement en mode sélection multiple
                actions = {
                    // Affiche le bouton "Supprimer" lorsque des produits sont sélectionnés.
                    // En cliquant dessus, l’événement `DeleteSelectedProducts` est déclenché,
                    // ce qui supprime les produits cochés de la base de données locale.
                    if (state.buttonDeleteIsVisible) {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(ProductsEvent.DeleteSelectedProducts(state.selectableActiveProducts))
                            }
                        ) {
                            Text(
                                text = "Supprimer",
                                modifier = Modifier.padding(end = 20.dp),
                                color = Color.White
                            )
                        }
                    }
                    Text(
                        text = "$currentSum €",
                        modifier = Modifier.padding(end = 20.dp),
                        color = Color.White
                    )
                }
            )
        },
        // Barre inférieure avec les actions principales :
        // - Tri (affiche SortDropDownMenu)
        // - Budgétisation (à implémenter)
        // - Activation du mode suppression multiple
        // + FloatingActionButton selon l’onglet actif.
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(ProductsEvent.ToggleSortDropDownMenu)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = "SortButton"
                        )
                        SortDropDownMenu(
                            expanded = state.sortDropDownExpanded,
                            onDismiss = {
                                viewModel.onEvent(ProductsEvent.ToggleSortDropDownMenu)
                            }
                        )
                    }
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.EuroSymbol,
                            contentDescription = "BudgetButton"
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(ProductsEvent.ToggleProductSelectionMode)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "DeleteButton"
                        )
                    }
                },
                // Bouton d’action flottant adapté à l’onglet actif :
                // - Onglet actif (0) : ajout d’un nouveau produit
                // - Onglet archivés (1) : restauration des produits archivés
                floatingActionButton = {
                    when (pagerState.currentPage) {
                        0 -> {
                            FloatingActionButton(containerColor = Color.Black,
                                contentColor = Color.White,
                                onClick = {
                                    editViewModel.prepareForNewProduct()
                                    viewModel.onEvent(ProductsEvent.ToggleBottomDialog)
                                }) {
                                Icon(Icons.Filled.Add, "Open Add dialog")
                            }
                        }
                        1 -> {
                            FloatingActionButton(containerColor = darkAccentColor,
                                contentColor = Color.White,
                                onClick = {
                                    viewModel.onEvent(ProductsEvent.RestoreAllProducts)
                                }) {
                                Icon(Icons.Filled.Restore, "Restore All")
                            }
                        }
                    }
                }
            )
        },

        floatingActionButtonPosition = FabPosition.End,
        modifier = modifier.fillMaxSize(),
    ) {
        if (state.showBottomSheet) {
            // Dialogue d’ajout/édition affiché en bas de l’écran si `showBottomSheet` est actif.
            AddEditDialog(
                onDismiss = {
                    viewModel.onEvent(ProductsEvent.ToggleBottomDialog)
                },
                modifier = Modifier
            )
        }
        // Contenu principal de l’écran :
        // - Onglets (`CustomTabRow`)
        // - Navigation entre listes (`CustomHorizontalPager`)
        // - Liste des produits actifs (`ShopList`)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .background(whiteColor)
        ) {
            CustomTabRow(pagerState = pagerState)

            CustomHorizontalPager(
                viewModel,
                editViewModel,
                pagerState
            )

            ShopList(
                viewModel = viewModel,
                editViewModel = editViewModel
            )
        }
    }
}











