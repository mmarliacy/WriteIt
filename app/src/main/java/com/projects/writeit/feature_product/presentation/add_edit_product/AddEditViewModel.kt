package com.projects.writeit.feature_product.presentation.add_edit_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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


/**
 * @HiltViewModel : ce ViewModel est injecté via Hilt (DI – Dependency Injection).
 *
 * On injecte productUseCases, un objet qui regroupe les cas d’utilisation liés aux produits
 * (ajouter, modifier, etc.).
 *
 * ViewModel() : la classe hérite de ViewModel, ce qui permet de conserver l'état entre les recompositions.
 */
@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private var currentProductId: Int? = null


    // -- Etat du nom du produit (modifiable + lecture seule).
    private val _productName = mutableStateOf(
        ProductTextFieldState(
            hint = "Nomme ton article..."
        )
    )
    val productName: State<ProductTextFieldState> = _productName

    // -- Etat de la quantité du produit (modifiable + lecture seule).
    private val _productQuantity = mutableStateOf(
        ProductTextFieldState(
            hint = "Combien... ?"
        )
    )
    val productQuantity: State<ProductTextFieldState> = _productQuantity

    // -- Etat de la prix du produit (modifiable + lecture seule).
    private val _productPrice = mutableStateOf(
        ProductTextFieldState(
            hint = "Combien ça coûte... ?"
        )
    )
    val productPrice: State<ProductTextFieldState> = _productPrice


    // -- MutableSharedFlow sert à émettre des événements ponctuels.
    // -- eventFlow est exposé en lecture seule pour l'UI avec asSharedFlow()
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    //---------------------------------------------------------------------------------------
    // -- VIEW MODEL FUNCTIONS -->
    //------------------------------------

    // -> Le rôle de cette fonction est de vider les champs de texte du formulaire, pour l'ajout d'un nouveau produit.
    fun prepareForNewProduct() {
        currentProductId = null
        _productPrice.value = productPrice.value.copy(
            priceText = ""
        )
        _productQuantity.value = productQuantity.value.copy(
            quantityText = ""
        )
        _productName.value = productName.value.copy(
            nameText = ""
        )
    }

    //---------------------------------------------------------------------------------------
    // -- ADD / EDIT : UI EVENTS -->
    //------------------------------------

    fun onEvent(event: AddEditProductEvent) {
        when (event) {

            // -> On copie l’état actuel du champ (productName.value)
            // -> et on remplace uniquement le nameText par la nouvelle valeur entrée par l’utilisateur.
            is AddEditProductEvent.EnteredName -> {
                _productName.value = productName.value.copy(
                    nameText = event.value
                )
            }

            // -> Le hint du nom du produit s'affiche si le champ de texte est vide et non visé.
            is AddEditProductEvent.ChangeNameFocus -> {
                _productName.value = productName.value.copy(
                    isHintVisible = !event.focusState.isFocused && productName.value.nameText.isBlank()

                )
            }

            // -> On copie l’état actuel du champ (productQuantity.value)
            // -> et on remplace uniquement le quantityText par la nouvelle valeur entrée par l’utilisateur.
            is AddEditProductEvent.EnteredQuantity -> {
                _productQuantity.value = productQuantity.value.copy(
                    quantityText = event.value
                )
            }

            // -> Le hint de la quantité du produit s'affiche si le champ de texte est vide et non visé.
            is AddEditProductEvent.ChangeQuantityFocus -> {
                _productQuantity.value = productQuantity.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            productQuantity.value.quantityText.isBlank()
                )
            }

            // -> On copie l’état actuel du champ (productPrice.value)
            // -> et on remplace uniquement le priceText par la nouvelle valeur entrée par l’utilisateur.
            is AddEditProductEvent.EnteredPrice -> {
                _productPrice.value = productPrice.value.copy(
                    priceText = event.value
                )
            }

            // -> Le hint du prix du produit s'affiche si le champ de texte est vide et non visé.
            is AddEditProductEvent.ChangePriceFocus -> {
                _productPrice.value = productPrice.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            productPrice.value.priceText.isBlank()
                )
            }

            // -> Reprendre et remplir le formulaire avec les données du produit à modifier.
            is AddEditProductEvent.InitProduct -> {
                currentProductId = event.product.id
                _productName.value = productName.value.copy(
                    nameText = event.product.name,
                    isHintVisible = false
                )
                _productQuantity.value = productQuantity.value.copy(
                    quantityText = event.product.quantity.toString(),
                    isHintVisible = false
                )
                _productPrice.value = productPrice.value.copy(
                    priceText = event.product.price.toString(),
                    isHintVisible = false
                )
            }

            // -> Crée un nouveau produit ou met à jour un ancien produit,
            // -> à partir des données renseignées dans le formulaire avec un "try/catch" pour gérer les exceptions.
            is AddEditProductEvent.SaveProduct -> {
                viewModelScope.launch {
                    try {
                        productUseCases.addProduct(
                            Product(
                                id = currentProductId,
                                name = productName.value.nameText,
                                timestamp = System.currentTimeMillis(),
                                quantity = productQuantity.value.quantityText.toInt(),
                                price = productPrice.value.priceText.toDouble()
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveProduct)
                        if (currentProductId == null) {
                            _eventFlow.emit(UiEvent.ShowSnackBar("${productName.value.nameText} a été ajouté à la liste"))
                        } else {
                            _eventFlow.emit(UiEvent.ShowSnackBar("${productName.value.nameText} a été mis à jour"))
                        }
                        prepareForNewProduct()

                    } catch (e: InvalidProductException) {
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

    /**
     * @UiEvent est une classe scellée qui regroupe les évènements ponctuels qui déclenche une action dans l'UI.
     */
    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data object SaveProduct : UiEvent()
    }
}

