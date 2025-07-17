package com.projects.writeit.feature_product.presentation.add_edit_product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.domain.use_case.ProductUseCases
import com.projects.writeit.feature_product.domain.util.InvalidProductException
import com.projects.writeit.feature_product.domain.util.ProductTextFieldState
import com.projects.writeit.feature_product.presentation.add_edit_product.util.AddEditProductEvent
import com.projects.writeit.feature_product.presentation.add_edit_product.util.AddEditProductState
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
    private val productUseCases: ProductUseCases,
) : ViewModel() {

    private var currentProductId: Int? = null

    private val _state = mutableStateOf(
        AddEditProductState()
    )
    val state: State<AddEditProductState> = _state

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

    private var originalProduct : Product? = null


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
        // Efface tous les messages d'erreurs précédents, pour les nouvelles entrées.
        _state.value = state.value.copy(
            nameError = null,
            priceError = null,
            quantityError = null
        )
    }

    // -> On ajoute le produit édité suivi de l'animation.
    // -- Fermer le dialogue et émettre un message dans l'UI en préparant le formulaire à un nouvel ajout/update.
    private suspend fun saveProductAndCloseDialog(message : String, product: Product) {
        productUseCases.insertProduct(product)
        _eventFlow.emit(UiEvent.ExitTheDialog)
        _eventFlow.emit(UiEvent.ShowSnackBar(message))
        prepareForNewProduct()
    }
    // -> Le rôle de cette fonction est de fermer le dialogue et
    // -- d'émettre un message dans l'UI en fonction de la logique donnée.
    private suspend fun emitExitWithMessage(message : String) {
        _eventFlow.emit(UiEvent.ExitTheDialog)
        _eventFlow.emit(UiEvent.ShowSnackBar(message))
    }

    fun initWithExistingProduct(product: Product) {
        // stocke-le si nécessaire
        originalProduct = product
    }

    //---------------------------------------------------------------------------------------
    // -- HANDLING FORM ERRORS -->
    //------------------------------------

    // Le rôle de cette fonction est de vérifier le nom du produit
    // S'il est vide `nameError` est mit à jour pour que l'erreur apparaisse au niveau de l'UI
    private fun checkName(nameToCheck: String): String {
        if (nameToCheck.isBlank()) {
            _state.value = state.value.copy(
                nameError = "Veuillez renseigner un nom de produit"
            )
        } else if (nameToCheck.isNotBlank()) {
            _state.value = state.value.copy(
                nameError = null
            )
        }
        return nameToCheck
    }

    // Le rôle de cette fonction est de vérifier la quantité du produit.
    // S'il est vide `nameError` est mit à jour pour que l'erreur apparaisse au niveau de l'UI
    private fun checkQuantity(quantityToCheck: String): String {
        if (quantityToCheck.isBlank()) {
            _state.value = state.value.copy(
                quantityError = "Veuillez renseigner la quantité"
            )
        } else if (quantityToCheck.isNotBlank()) {
            _state.value = state.value.copy(
                quantityError = null
            )
        }
        return quantityToCheck
    }

    // Le rôle de cette fonction est de vérifier le prix du produit et de le normaliser au besoin
    // S'il est vide `priceError` est mit à jour pour que l'erreur apparaisse au niveau de l'UI
    private fun checkPrice(priceToCheck: String): String {
        val normalized: String
        if (priceToCheck.contains(",")) {
            // On remplace toute virgule par un point
            normalized = priceToCheck.replace(",", ".").trim()
            return normalized
        } else if (priceToCheck.isBlank()) {
            _state.value = state.value.copy(
                priceError = "Veuillez renseigner le prix"
            )
        } else if (priceToCheck.isNotBlank()) {
            _state.value = state.value.copy(
                priceError = null
            )
        }
        return priceToCheck
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
            is AddEditProductEvent.GetProductToEdit -> {
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
                        // Vérifier toutes les valeurs et les valider à travers les fonctions de checking.
                        val checkedName = checkName(productName.value.nameText)
                        val checkedPrice = checkPrice(productPrice.value.priceText)
                        val checkedQuantity = checkQuantity(productQuantity.value.quantityText)
                        val editedProduct = Product(
                            id = currentProductId,
                            name = checkedName,
                            timestamp = System.currentTimeMillis(),
                            quantity = checkedQuantity.toInt(),
                            price = checkedPrice.toDouble()
                        )
                        if (currentProductId == null) {
                            // -- Si l'id est nul (nouveau produit), on l'ajoute, clôture le dialogue
                            // -- et prépare le formulaire pour un nouvel ajout.
                            saveProductAndCloseDialog(
                                message = "${productName.value.nameText} a été ajouté à la liste",
                                product = editedProduct)
                        } else {
                            // -- Si l'id n'est pas nul (produit existant), on récupère le produit original stocké dans le ProductViewModel.
                            // -- Le timestamp (de création) étant différent on en copie la valeur pour comparé le produit original à celui qu'on a édité.
                            // -- Si les autres constantes n'ont pas changés, on revient à la liste et on annule l'opération.
                            // -- Si au moins une constante a changée, on insère le produit modifié.
                            if (originalProduct != null) {
                                val originalForCompare =
                                    originalProduct!!.copy(timestamp = editedProduct.timestamp)
                                if (editedProduct == originalForCompare) {
                                    emitExitWithMessage("Aucune modification sur le produit.")
                                } else {
                                    saveProductAndCloseDialog(
                                        message = "${productName.value.nameText} a été mis à jour",
                                        product = editedProduct)
                                }
                            } else {
                                // Pas de produit original en édition
                                emitExitWithMessage("Impossible de retrouver le produit d’origine")
                            }
                        }
                    } catch (e: InvalidProductException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Produit non ajouté"
                            )
                        )
                    } catch (e: NumberFormatException) {
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
        data object ExitTheDialog : UiEvent()
    }
}

