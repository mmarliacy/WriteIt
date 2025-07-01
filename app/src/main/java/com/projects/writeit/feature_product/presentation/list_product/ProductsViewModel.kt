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

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ProductsState())
    val state: State<ProductsState> = _state

    private var recentlyDeletedProduct: Product? = null

    private var getActiveProductsJob: Job? = null
    private var getArchivedProductsJob: Job? = null

    val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _productToEdit = mutableStateOf<Product?>(null)
    val productToEdit: State<Product?> = _productToEdit

    fun productToEdit(product: Product){
        _productToEdit.value = product
    }

    init {
        getActiveProducts(productOrder = ProductOrder.Date(OrderType.AscendingOrder))
        getArchivedProducts(productOrder = ProductOrder.Date(OrderType.AscendingOrder))
    }

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

            //-------------------------------------------------------
            //----> ARCHIVE/DIS-ARCHIVE ONE OR MANY PRODUCTS  -------------->>>>>
            //-------------------------------------------------------
            is ProductsEvent.ArchiveProduct -> {
                viewModelScope.launch {
                    val archivedProduct = event.product.copy(isArchived = true)
                    productUseCases.pInsertProduct(archivedProduct)
                    _eventFlow.emit(UiEvent.ShowSnackBar("${archivedProduct.name} a été archivé"))
                }
            }

            is ProductsEvent.DisArchiveProduct -> {
                viewModelScope.launch {
                    val disArchiveProduct = event.product.copy(isArchived = false)
                    productUseCases.pInsertProduct(disArchiveProduct)
                    _eventFlow.emit(UiEvent.ShowSnackBar("${disArchiveProduct.name} est de nouveau dans ta liste"))
                }
            }

            //-------------------------------------------------------
            //----> RESTORE ONE OR MANY PRODUCTS  -------------->>>>>
            //-------------------------------------------------------
            is ProductsEvent.RestoreProduct -> {
                viewModelScope.launch {
                    productUseCases.pInsertProduct(recentlyDeletedProduct ?: return@launch)
                    recentlyDeletedProduct = null
                    _eventFlow.emit(UiEvent.ShowSnackBar("Produit remis dans la liste"))
                }
            }

            is ProductsEvent.RestoreAllProducts -> {
                viewModelScope.launch {
                    productUseCases.getArchivedProducts()
                        .first()
                        .onEach { oldProduct ->
                            val productToRestore = oldProduct.copy(
                                isArchived = false
                            )
                            productUseCases.pInsertProduct(productToRestore)
                        }
                    _eventFlow.emit(UiEvent.ShowSnackBar("Tous les produits ont été restaurés"))
                }
            }

            //-------------------------------------------------------
            //----> TOGGLE & HANDLE PRODUCTS TO DELETE --------->>>>>
            //-------------------------------------------------------

            is ProductsEvent.ToggleProductSelectionMode -> {
                _state.value = state.value.copy(
                    isSelectionMode = !state.value.isSelectionMode
                )
            }



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
            //--------------------------------------------
            //----> DELETE SELECTED PRODUCT --------->>>>>
            //--------------------------------------------
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

            ProductsEvent.ToggleSortDropDownMenu -> TODO()
        }
    }

    //--------------------------------------------
    //----> GET INITIAL PRODUCTS ------------>>>>>
    //--------------------------------------------
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

    //---------------------------------------------
    //----> GET ARCHIVED PRODUCTS ------------>>>>>
    //---------------------------------------------
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
    // -- ONE TIME UI EVENT -->
    //------------------------------------
    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }
}