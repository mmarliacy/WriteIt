package com.projects.writeit.feature_product.presentation.list_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.use_case.ProductUseCases
import com.projects.writeit.feature_product.domain.util.OrderType
import com.projects.writeit.feature_product.domain.util.ProductOrder
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsEvent
import com.projects.writeit.feature_product.presentation.list_product.util.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ProductsState())
    val state: State<ProductsState> = _state

    private var recentlyDeletedProduct: Product? = null

    private var getProductsJob: Job? = null

    init {
        getProducts(productOrder = ProductOrder.Date(OrderType.AscendingOrder))
    }

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.Order -> {
                if (state.value.productsOrder::class == event.productOrder::class &&
                    state.value.productsOrder.orderType == event.productOrder.orderType
                ) {
                    return
                }
                getProducts(event.productOrder)
            }

            is ProductsEvent.DeleteProduct -> {
                viewModelScope.launch {
                    productUseCases.deleteProduct(event.product)
                    recentlyDeletedProduct = event.product
                }
            }

            is ProductsEvent.RestoreProduct -> {
                viewModelScope.launch {
                    productUseCases.addProduct(recentlyDeletedProduct ?: return@launch )
                    recentlyDeletedProduct = null
                }
            }
        }
    }

    private fun getProducts(productOrder: ProductOrder) {
        getProductsJob?.cancel()
        getProductsJob = productUseCases.getProducts(productOrder).onEach {
                _state.value = state.value.copy(
                    products = it,
                    productsOrder = productOrder
                )
        }.launchIn(viewModelScope)
    }
}