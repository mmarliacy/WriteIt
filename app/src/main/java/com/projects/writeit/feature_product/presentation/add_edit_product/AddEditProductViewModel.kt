package com.projects.writeit.feature_product.presentation.add_edit_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.writeit.feature_product.domain.model.InvalidProductException
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.use_case.ProductUseCases
import com.projects.writeit.feature_product.domain.util.ProductTextFieldState
import com.projects.writeit.feature_product.presentation.add_edit_product.util.AddEditProductEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentProductId : Int? = null

    init {
        savedStateHandle.get<Int>("getId")?.let { productId ->
            if (productId != -1){
                viewModelScope.launch {
                    productUseCases.getProduct(productId)?.also { product ->
                        currentProductId = product.id
                        _productName.value = productName.value.copy(
                            text = product.name,
                            isHintVisible = false
                        )
                        _productQuantity.value = productQuantity.value.copy(
                            quantity = product.quantity,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    // 1 -- Default State of Product title -->
    private val _productName = mutableStateOf(ProductTextFieldState(
        hint = "Nomme ton article..."
    ))
    val productName: State<ProductTextFieldState> = _productName

    // 2 -- Default State of Product quantity -->
    private val _productQuantity = mutableStateOf(ProductTextFieldState(
        hint = "Combien... ?"
    ))
    val productQuantity: State<ProductTextFieldState> = _productQuantity

    /** State for one event action */
    private val _eventFlow = MutableSharedFlow<UiEvent>()
            val eventFlow = _eventFlow.asSharedFlow()


    //---------------------------------------------------------------------------------------
    // -- ADD EDIT UI EVENT -->
    //------------------------------------
    fun onEvent(event: AddEditProductEvent) {
        when(event){
            is AddEditProductEvent.EnteredTitle -> {
                _productName.value = productName.value.copy(
                    text = event.value
                )
            }
            is AddEditProductEvent.ChangeTitleFocus -> {
                _productName.value = productName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            productName.value.text.isBlank()
                )
            }
            is AddEditProductEvent.EnteredQuantity -> {
                _productQuantity.value = productQuantity.value.copy(
                    text = event.value
                )
            }
            is AddEditProductEvent.ChangeQuantityFocus -> {
                _productQuantity.value = productQuantity.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            productQuantity.value.text.isBlank()
                )
            }
            is AddEditProductEvent.SaveProduct -> {
                viewModelScope.launch {
                    try {
                        productUseCases.addProduct(
                            Product(
                                id = currentProductId,
                                name = productName.value.text,
                                timestamp = System.currentTimeMillis(),
                                quantity = productQuantity.value.quantity
                                )
                        )

                        _eventFlow.emit(UiEvent.SaveProduct)

                    } catch(e: InvalidProductException){
                      _eventFlow.emit(
                          UiEvent.ShowSnackBar(
                              message = e.message ?: "Produit non ajouté"
                          )
                      )
                    }
                }
            }
        }

    }

    //---------------------------------------------------------------------------------------
    // -- ONE TIME UI EVENT -->
    //------------------------------------
    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data object SaveProduct : UiEvent()
    }


}