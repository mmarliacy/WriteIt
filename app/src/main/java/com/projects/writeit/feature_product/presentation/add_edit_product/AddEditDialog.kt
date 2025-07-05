package com.projects.writeit.feature_product.presentation.add_edit_product


//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.components.CustomButton
import com.projects.writeit.feature_product.presentation.add_edit_product.components.CustomTitle
import com.projects.writeit.feature_product.presentation.add_edit_product.components.TransparentTextField
import com.projects.writeit.feature_product.presentation.add_edit_product.util.AddEditProductEvent
import com.projects.writeit.ui.theme.darkAccentColor
import com.projects.writeit.ui.theme.latoFamily
import kotlinx.coroutines.flow.collectLatest

/**
 * Composable représentant une feuille modale (Bottom Sheet) permettant à l'utilisateur
 * d'ajouter ou modifier un produit (nom, quantité, prix).
 *
 * Il utilise le ViewModel `AddEditProductViewModel` pour gérer l'état des champs et la logique métier.
 * Chaque champ est relié à un `TextFieldState` personnalisé.
 *
 * La feuille s'affiche via `ModalBottomSheet` et se ferme automatiquement après la sauvegarde du produit,
 * grâce à la collecte d’un événement `UiEvent.SaveProduct`.
 *
 * @param onDismiss Fonction appelée lors de la fermeture du dialogue.
 * @param viewModel ViewModel injecté pour gérer l’état et les événements.
 * @param modifier Permet d'ajuster l’apparence depuis l’extérieur si nécessaire.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDialog(
    onDismiss: () -> Unit,
    viewModel: AddEditViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val state = viewModel.state.value

    // -- Etats des caractéristiques du produit utilisable en lecture seule dans l'UI, provenant du View Model.
    val productNameState = viewModel.productName.value
    val productQuantityState = viewModel.productQuantity.value
    val productPriceState = viewModel.productPrice.value

    //---------------------------------------------------------------------------------------
    // -> Effet lancé une seule fois à la composition du composable.
    // -> Il observe les événements émis par le ViewModel via `eventFlow`.
    // -> Après une sauvegarde réussie on appelle `onDismiss()` pour fermer automatiquement le dialogue.
    //------------------------------------
    LaunchedEffect(
        key1 = true
    ) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditViewModel.UiEvent.ExitTheDialog -> {
                    onDismiss()
                }
                else -> Unit
            }
        }
    }

    //---------------------------------------------------------------------------------------
    // -- DIALOGUE D'AJOUT DE PRODUIT -->
    // -> Boîte de dialogue qui s'affiche en bas de l'écran et contient l'ensemble du formulaire d'ajout/édition de produit.
    // -> Se ferme soit en balayant vers le bas, soit en appelant la fonction `onDismiss`.
    //------------------------------------
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = Color.White,

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            CustomTitle(
                title = "Ajoute ton produit",
                fontFamily = latoFamily,
                fontWeight = FontWeight.SemiBold,
                size = 25.sp,
                fontStyle = FontStyle.Normal
            )
        }
        HorizontalDivider(
            thickness = 0.5.dp,
            color = darkAccentColor,
            modifier = modifier.fillMaxWidth()
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .padding(15.dp)
        ) {

            //---------------------------------------------------------------------------------------
            // -- CHAMPS DE TEXTE A REMPLIR -->
            // -> Champs de texte reliés au ViewModel des événements,
            // -> qui permettent de saisir le nom, la quantité, et le prix d'un produit,
            // -- Selon des conditions (error) définies (Non vide, > 0) qui filtre les erreurs.
            //------------------------------------

            TransparentTextField(
                modifier = modifier.testTag("nameText"),
                text = productNameState.nameText,
                hint = productNameState.hint,
                onValueChange = { productName ->
                    viewModel.onEvent(AddEditProductEvent.EnteredName(productName))
                },
                onFocusChange = { productName ->
                    viewModel.onEvent(AddEditProductEvent.ChangeNameFocus(productName))
                },
                isHintVisible = productNameState.isHintVisible,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = state.nameError != null,
                supportingErrorText = state.nameError
                )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            // -> Champ pour saisir la quantité.
            TransparentTextField(
                modifier = modifier.testTag("quantityText"),
                text = productQuantityState.quantityText,
                hint = productQuantityState.hint,
                onValueChange = { productQuantity ->
                    viewModel.onEvent(
                        AddEditProductEvent.EnteredQuantity(
                            productQuantity
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onFocusChange = { productQuantity ->
                    viewModel.onEvent(
                        AddEditProductEvent.ChangeQuantityFocus(
                            productQuantity
                        )
                    )
                },
                isHintVisible = productQuantityState.isHintVisible,
                isError = state.quantityError != null,
                supportingErrorText = state.quantityError
            )
            Spacer(
                modifier = Modifier.height(5.dp)
            )

            // -> Champ pour saisir le prix.
            TransparentTextField(
                modifier = modifier.testTag("priceText"),
                text = productPriceState.priceText,
                hint = productPriceState.hint,
                onValueChange = { productPrice ->
                    viewModel.onEvent(AddEditProductEvent.EnteredPrice(productPrice))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onFocusChange = { productPrice ->
                    viewModel.onEvent(AddEditProductEvent.ChangePriceFocus(productPrice))
                },
                isHintVisible = productPriceState.isHintVisible,
                isError = state.priceError != null,
                supportingErrorText = state.priceError
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // -> Bouton "OK" personnalisé pour enregistrer le produit dans la base de données locale.
            CustomButton {
                viewModel.onEvent(AddEditProductEvent.SaveProduct)
            }
        }
    }
}
