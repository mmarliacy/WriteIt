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

    private val _state = mutableStateOf(ProductsState())
    val state: State<ProductsState> = _state

    private var recentlyDeletedProduct: Product? = null

    private var getActiveProductsJob: Job? = null
    private var getArchivedProductsJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Produit dont l'id est null et qio sert de réceptacle pour le produit à éditer.
    private val _productToEdit = mutableStateOf<Product?>(null)
    val productToEdit: State<Product?> = _productToEdit

    fun productToEdit(product: Product) {
        _productToEdit.value = product
    }

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
            is ProductsEvent.Order -> {
                if (state.value.productsOrder::class == event.productOrder::class &&
                    state.value.productsOrder.orderType == event.productOrder.orderType
                ) {
                    return
                }
                getActiveProducts(event.productOrder)
            }

            is ProductsEvent.ToggleBottomDialog -> {
                _state.value = state.value.copy(
                    showBottomSheet = !state.value.showBottomSheet
                )
            }

            is ProductsEvent.ToggleSortDropDownMenu -> {
                _state.value = state.value.copy(
                    sortDropDownExpanded = !state.value.sortDropDownExpanded
                )
            }

            is ProductsEvent.ArchiveProduct -> {
                viewModelScope.launch {
                    val archivedProduct = event.product.copy(isArchived = true)
                    productUseCases.insertProduct(archivedProduct)
                    _eventFlow.emit(UiEvent.ShowSnackBar("${archivedProduct.name} a été archivé"))
                }
            }

            is ProductsEvent.DisArchiveProduct -> {
                viewModelScope.launch {
                    val disArchiveProduct = event.product.copy(isArchived = false)
                    productUseCases.insertProduct(disArchiveProduct)
                    _eventFlow.emit(UiEvent.ShowSnackBar("${disArchiveProduct.name} est de nouveau dans ta liste"))
                }
            }

            // Restaure le dernier produit supprimé (stocké temporairement dans `recentlyDeletedProduct`).
            // Réinsère le produit dans la base et réinitialise le cache local.
            // Affiche une snackbar pour confirmer la restauration.
            is ProductsEvent.RestoreProduct -> {
                viewModelScope.launch {
                    productUseCases.insertProduct(recentlyDeletedProduct ?: return@launch)
                    recentlyDeletedProduct = null
                    _eventFlow.emit(UiEvent.ShowSnackBar("Produit remis dans la liste"))
                }
            }

            // Restaure tous les produits archivés en les marquant comme non archivés.
            // Pour chaque produit, on l’insère de nouveau dans la base de données avec `isArchived = false`.
            // Affiche une snackbar globale une fois l'opération terminée.
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
                    val selectedItemsList = state.value.selectableActiveProducts
                        .filter { it.isChecked }.map {
                            it.product
                        }

                    selectedItemsList.forEach {
                        productUseCases.deleteProduct(it)
                    }
                    Log.d("SNACKBAR", "Suppression détectée, affichage snackbar")
                    _eventFlow.emit(UiEvent.ShowSnackBar("${selectedItemsList.size} produit(s) supprimé(s)"))
                    val updatedList = state.value.selectableActiveProducts
                        .filter { !it.isChecked }
                        .map { it.copy(isChecked = false) }
                    _state.value = state.value.copy(
                        selectableActiveProducts = updatedList,
                        isSelectionMode = false,
                        buttonDeleteIsVisible = false
                    )

                }
            }
        }
    }

    // Événements ponctuels envoyés à l’UI, comme l'affichage de snackbars.
    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }
}