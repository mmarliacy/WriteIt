package com.projects.writeit.feature_product.presentation.add_edit_product


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAddEditDialog(
    onDismiss: () -> Unit,
    viewModel: AddEditProductViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val productNameState = viewModel.productName.value
    val productQuantityState = viewModel.productQuantity.value
    val productPriceState = viewModel.productPrice.value
    val scaffoldState = rememberScaffoldState()


    LaunchedEffect(
        key1 = true
    ) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditProductViewModel.UiEvent.SaveProduct -> {
                    onDismiss()
                }

                else -> Unit
            }
        }
    }

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
            TransparentTextField(
                text = productNameState.text,
                hint = productNameState.hint,
                onValueChange = { productName ->
                    viewModel.onEvent(AddEditProductEvent.EnteredTitle(productName))
                },
                onFocusChange = { productName ->
                    viewModel.onEvent(AddEditProductEvent.ChangeTitleFocus(productName))
                },
                isHintVisible = productNameState.isHintVisible,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            TransparentTextField(
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
                isHintVisible = productQuantityState.isHintVisible
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            TransparentTextField(
                text = productPriceState.priceText,
                hint = productPriceState.hint,
                onValueChange = { productPrice ->
                    viewModel.onEvent(AddEditProductEvent.EnteredPrice(productPrice))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onFocusChange = { productPrice ->
                    viewModel.onEvent(AddEditProductEvent.ChangePriceFocus(productPrice))
                },
                isHintVisible = productPriceState.isHintVisible
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton {
                viewModel.onEvent(AddEditProductEvent.SaveProduct)
            }
        }
    }
}
