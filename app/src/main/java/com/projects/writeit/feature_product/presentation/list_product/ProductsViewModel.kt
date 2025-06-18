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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

            is ProductsEvent.DeleteProduct -> {
                viewModelScope.launch {
                    productUseCases.deleteProduct(event.product)
                    recentlyDeletedProduct = event.product
                }
            }

            is ProductsEvent.ArchiveProduct -> {
                viewModelScope.launch {
                    val archivedProduct = event.product.copy(isArchived = true)
                    productUseCases.addProduct(archivedProduct)
                    // _eventFlow.emit(UiEvent.ShowSnackbar("Produit archivé"))
                }
            }

            is ProductsEvent.DisArchiveProduct -> {
                viewModelScope.launch {
                    val disArchiveProduct = event.product.copy(isArchived = false)
                    productUseCases.addProduct(disArchiveProduct)
                    // _eventFlow.emit(UiEvent.ShowSnackbar("Produit archivé"))
                }
            }

            is ProductsEvent.RestoreProduct -> {
                viewModelScope.launch {
                    productUseCases.addProduct(recentlyDeletedProduct ?: return@launch)
                    recentlyDeletedProduct = null
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
                            productUseCases.addProduct(productToRestore)
                        }
                }
                // _eventFlow.emit(UiEvent.ShowSnackbar("Tous les produits ont été restaurés"))
            }

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

                    if (state.value.selectableActiveProducts.any { it.isChecked }){
                        _state.value = state.value.copy(buttonDeleteIsVisible = true)
                    } else {
                        _state.value = state.value.copy(buttonDeleteIsVisible = false)
                    }
                }
            }

            is ProductsEvent.DeleteSelectedProducts -> {
                val selectedItemsList = state.value.selectableActiveProducts
                    .filter { it.isChecked }.map {
                        it.product
                    }

                selectedItemsList.forEach {
                    viewModelScope.launch {
                        productUseCases.deleteProduct(it)
                    }
                }
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

    private fun getArchivedProducts(productOrder: ProductOrder) {
        getArchivedProductsJob?.cancel()
        getArchivedProductsJob = productUseCases.getArchivedProducts(productOrder).onEach {
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
}