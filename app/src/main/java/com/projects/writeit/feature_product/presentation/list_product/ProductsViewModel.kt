package com.projects.writeit.feature_product.presentation.list_product

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.use_case.ProductUseCases
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsState
import com.projects.writeit.feature_product.presentation.list_product.util.SelectableProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

/**
 * ViewModel principal chargé de gérer l’état, les interactions et les événements liés aux produits
 * (actifs ou archivés). Il utilise des StateFlows, MutableState et coroutines pour gérer :
 *
 * - La récupération, suppression, l'archivage ou la restauration des produits
 * - La sélection multiple pour suppression
 * - Le tri dynamique des produits
 * - L’émission d’événements UI (snackbars)
 */
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    // Etat des éléments faisant parti de l'écran de gestion des produits.
    private val _state = mutableStateOf(ProductsState())
    val state: State<ProductsState> = _state

    // Produit récemment supprimé à ré-intégrer dans une méthode "UNDO" -> TODO ()
    private var recentlyDeletedProduct: Product? = null

    // Travail asynchrone qui permet de contrôler la récupération de la liste des produits (actifs/archivés).
    private var getActiveProductsJob: Job? = null
    private var getArchivedProductsJob: Job? = null

    // Flow permettant d'émettre des évènements uniques vers l'UI.
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Produit dont l'id est null et qui sert de réceptacle pour le produit à éditer.
    private val _productToEdit = mutableStateOf<Product?>(null)
    val productToEdit: State<Product?> = _productToEdit

    // Au lancement du ViewModel, on récupère les produits actifs et archivés.
    init {
        getActiveProducts(productOrder = ProductOrder.Date(OrderType.AscendingOrder))
        getArchivedProducts(productOrder = ProductOrder.Date(OrderType.AscendingOrder))
    }

    //---------------------------------------------------------------------------------------
    // -- VIEW MODEL : FUNCTIONS -->
    //------------------------------------

    // Calcule la somme totale des produits actifs.
    // Le calcul est mis à jour automatiquement via snapshotFlow et arrondi à 2 décimales.
    val totalPriceSum: StateFlow<Double> = snapshotFlow { state.value.selectableActiveProducts }
        .map { liste ->
            Log.i(
                "SUM",
                "Calcul de somme: ${liste.sumOf { it.product.price * it.product.quantity }}"
            )
            val total = liste.sumOf { it.product.price * it.product.quantity }
            val arrondi = BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toDouble()
            Log.i("SUM", "Calcul de somme: $arrondi")
            arrondi
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    // On récupère les informations du produit à éditer.
    fun productToEdit(product: Product) {
        _productToEdit.value = product
    }


    // Récupère les produits actifs depuis la couche UseCases,
    // puis les transforme en liste de SelectableProduct pour l'UI.
    private fun getActiveProducts(productOrder: ProductOrder) {
        getActiveProductsJob?.cancel()
        getActiveProductsJob = productUseCases.getProducts(productOrder).onEach {
            val selectableList = it.map { product ->
                SelectableProduct(product = product)
            }
            _state.value = state.value.copy(
                selectableActiveProducts = selectableList,
                productsOrder = productOrder
            )
        }.catch {
            _eventFlow.emit(UiEvent.ShowSnackBar("La liste n'a pas pu être récupérée, patientez un instant..."))
        }.launchIn(viewModelScope)
        Log.i("DEBUG", "Produits: ${state.value.selectableActiveProducts.map { it.product.name }}")
    }

    // Récupère les produits archivés.
    private fun getArchivedProducts(productOrder: ProductOrder) {
        getArchivedProductsJob?.cancel()
        getArchivedProductsJob = productUseCases.getArchivedProducts().onEach {
            _state.value = state.value.copy(
                archivedProducts = it,
                productsOrder = productOrder
            )
        }.catch {
            _eventFlow.emit(UiEvent.ShowSnackBar("La liste n'a pas pu être récupérée, patientez un instant..."))
        }.launchIn(viewModelScope)
        Log.i(
            "ARCHIVE DEBUG",
            "Produits archivés : ${state.value.selectableActiveProducts.map { it.product.name }}"
        )
    }

    //---------------------------------------------------------------------------------------
    // -- LISTS : UI EVENTS -->
    // -----------------------------------
    // - Gère tous les événements déclenchés depuis l’UI.
    // - Chaque type d’événement correspond à une action spécifique sur l’état ou la base de données :
    // - Tri, affichage du bottom sheet, menu de tri
    // - Archivage, désarchivage
    // - Restauration de produits
    // - Activation/sélection pour suppression
    // - Suppression définitive
    //------------------------------------
    fun onEvent(event: ProductsEvent) {
        when (event) {
            // Appelle la liste de produits de manière ordonnée.
            is ProductsEvent.Order -> {
                if (state.value.productsOrder::class == event.productOrder::class &&
                    state.value.productsOrder.orderType == event.productOrder.orderType
                ) {
                    return
                }
                getActiveProducts(event.productOrder)
            }

            // Active/Désactive le dialogue d'édition de produit.
            is ProductsEvent.ToggleBottomDialog -> {
                _state.value = state.value.copy(
                    showBottomSheet = !state.value.showBottomSheet
                )
            }

            // Active/Désactive le menu déroulant de tri.
            is ProductsEvent.ToggleSortDropDownMenu -> {
                _state.value = state.value.copy(
                    sortDropDownExpanded = !state.value.sortDropDownExpanded
                )
            }

            // Archive le produit en lui attribuant la propriété "isArchived"
            // On insère le produit dans la liste des produits archivés.
            // Affiche un snackbar pour confirmer l'insertion / notifier l'échec de l'insertion.
            is ProductsEvent.ArchiveProduct -> {
                val archivedProduct = event.product.copy(isArchived = true)
                viewModelScope.launch {
                    try {
                        productUseCases.insertProduct(archivedProduct)
                        _eventFlow.emit(UiEvent.ShowSnackBar("${archivedProduct.name} a été archivé"))
                    } catch (e : Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar("${archivedProduct.name} n'a pas été archivé"))
                    }
                }
            }

            // Onb ré-insère le produit en lui otant la propriété "isArchived"
            // On le remet à nouveau dans la liste des produits actifs.
            // Affiche un snackbar pour confirmer l'insertion / notifier l'échec de l'insertion.
            is ProductsEvent.DisArchiveProduct -> {
                val disArchiveProduct = event.product.copy(isArchived = false)
                viewModelScope.launch {
                    try {
                        productUseCases.insertProduct(disArchiveProduct)
                        _eventFlow.emit(UiEvent.ShowSnackBar("${disArchiveProduct.name} est de nouveau dans ta liste"))
                    } catch (e : Exception) {
                        _eventFlow.emit(UiEvent.ShowSnackBar("${disArchiveProduct.name} n'a pas pu être réintégré"))
                    }
                }
            }

            // Restaure le dernier produit supprimé (stocké temporairement dans `recentlyDeletedProduct`).
            // Réinsère le produit dans la base et réinitialise le cache local.
            // Affiche un snackbar pour confirmer la restauration.
            is ProductsEvent.RestoreProduct -> {
                viewModelScope.launch {
                    productUseCases.insertProduct(recentlyDeletedProduct ?: return@launch)
                    recentlyDeletedProduct = null
                    _eventFlow.emit(UiEvent.ShowSnackBar("Produit remis dans la liste"))
                }
            }

            // Restaure tous les produits archivés en les marquant comme non archivés.
            // Pour chaque produit, on l’insère de nouveau dans la base de données avec `isArchived = false`.
            // Affiche un snackbar globale une fois l'opération terminée.
            is ProductsEvent.RestoreAllProducts -> {
                viewModelScope.launch {
                    productUseCases.getArchivedProducts()
                        .first()
                        .onEach { oldProduct ->
                            val productToRestore = oldProduct.copy(
                                isArchived = false
                            )
                            productUseCases.insertProduct(productToRestore)
                        }
                    _eventFlow.emit(UiEvent.ShowSnackBar("Tous les produits ont été restaurés"))
                }
            }

            // Active ou désactive le mode de sélection multiple pour la suppression.
            // Lorsque activé, des cases à cocher apparaissent dans la liste des produits actifs.
            is ProductsEvent.ToggleProductSelectionMode -> {
                _state.value = state.value.copy(
                    isSelectionMode = !state.value.isSelectionMode
                )
            }

            // Coche ou décoche un produit dans la liste des actifs pour une suppression multiple.
            // Met à jour dynamiquement la liste avec l’état de chaque case à cocher.
            // Affiche ou masque le bouton "Supprimer" selon la présence d’au moins un produit sélectionné.
            is ProductsEvent.ToggleProductSelection -> {
                viewModelScope.launch {
                    val updatedList =
                        state.value.selectableActiveProducts.map { selectableProduct ->
                            if (selectableProduct.product.id == event.productId) {
                                selectableProduct.copy(isChecked = !selectableProduct.isChecked)
                            } else selectableProduct
                        }
                    _state.value = state.value.copy(selectableActiveProducts = updatedList)

                    if (state.value.selectableActiveProducts.any { it.isChecked }) {
                        _state.value = state.value.copy(buttonDeleteIsVisible = true)
                    } else {
                        _state.value = state.value.copy(buttonDeleteIsVisible = false)
                    }
                }
            }

            // Supprime tous les produits sélectionnés,
            // puis met à jour la liste et désactive le mode de sélection.
            is ProductsEvent.DeleteSelectedProducts -> {
                viewModelScope.launch {
                    var undeletedProducts = 0
                    val itemsToDelete = state.value.selectableActiveProducts
                        .filter { it.isChecked }.map {
                            it.product
                        }
                    // -> Si aucun item n'est sélectionné, on le notifie à l'utilisateur
                    if (itemsToDelete.isEmpty()) {
                        _eventFlow.emit(UiEvent.ShowSnackBar("Sélectionnez au moins un produit à supprimer"))
                    } else {
                        var updatedList : List<SelectableProduct> = emptyList()

                        itemsToDelete.forEach {
                            try {
                                productUseCases.deleteProduct(it)
                            } catch (e: Exception) {
                                undeletedProducts++
                            }
                        }
                        // Si tous les produits ont été supprimés on désactive le mode suppression
                        // et on le notifie à l'utilisateur.
                        if(undeletedProducts == 0){
                            updatedList = state.value.selectableActiveProducts
                                .filter {!it.isChecked }
                                .map { it.copy(isChecked = false)}
                            _state.value = state.value.copy(
                                selectableActiveProducts = updatedList,
                                isSelectionMode = false,
                                buttonDeleteIsVisible = false
                            )
                            _eventFlow.emit(UiEvent.ShowSnackBar("${itemsToDelete.size - undeletedProducts} produit(s) supprimé(s)"))
                        } else {
                            // Liste des produits non sélectionnés pour la suppression
                            // qu'on souhaite récupérer pour une nouvelle suppression
                            val failedProducts = state.value.selectableActiveProducts
                                .filter {it.isChecked}
                                .map {it.product}
                            _state.value = state.value.copy(
                                selectableActiveProducts = updatedList,
                                isSelectionMode = failedProducts.isNotEmpty(),  // Reste en mode selection si erreurs
                                buttonDeleteIsVisible = failedProducts.isNotEmpty()
                            )
                            _eventFlow.emit(UiEvent.ShowSnackBar("${itemsToDelete.size - undeletedProducts} produit(s) supprimé(s), $undeletedProducts en échec"))
                        }

                    }
                }
            }
        }
    }

    // Événements ponctuels envoyés à l’UI, comme l'affichage de snackbars.
    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }
}