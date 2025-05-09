package com.projects.writeit.feature_product.presentation.add_edit_product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.projects.writeit.feature_product.presentation.add_edit_product.components.CustomButton
import com.projects.writeit.feature_product.presentation.add_edit_product.components.CustomTitle
import com.projects.writeit.feature_product.presentation.add_edit_product.components.TransparentTextField
import com.projects.writeit.feature_product.presentation.add_edit_product.util.AddEditProductEvent
import com.projects.writeit.ui.theme.whiteColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {

    val productNameState = viewModel.productName.value
    val productQuantityState = viewModel.productQuantity.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(
        key1 = true
    ) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditProductViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditProductViewModel.UiEvent.SaveProduct -> {
                        navController.navigateUp()
                }
            }
        }
    }


    Dialog(
        onDismissRequest = {
            // onDismissRequest()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(30.dp),
            shape = RoundedCornerShape(10.dp),
            color = whiteColor
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                CustomTitle(
                    title = "Ajoute ton produit",
                    size = 20.sp,
                    fontStyle = FontStyle.Normal
                )
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TransparentTextField(
                    text = productNameState.text,
                    hint = productNameState.hint,
                    onValueChange = { productName ->
                        scope.launch {
                            viewModel.onEvent(AddEditProductEvent.EnteredTitle(productName))
                        }
                    },
                    onFocusChange = { productName ->
                        scope.launch {
                            viewModel.onEvent(AddEditProductEvent.ChangeTitleFocus(productName))
                        }
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            TransparentTextField(
                text = productQuantityState.text,
                hint = productQuantityState.hint,
                onValueChange = { productQuantity ->
                    scope.launch {
                        viewModel.onEvent(AddEditProductEvent.EnteredQuantity(productQuantity))
                    }
                },
                onFocusChange = { productQuantity ->
                    scope.launch {
                        viewModel.onEvent(AddEditProductEvent.ChangeQuantityFocus(productQuantity))
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )
            CustomButton {
                viewModel.onEvent(AddEditProductEvent.SaveProduct)
            }
        }

    }
}