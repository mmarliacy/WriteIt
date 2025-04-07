package com.projects.writeit.feature_product.presentation.add_edit_product.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.presentation.products.MainViewModel
import com.projects.writeit.ui.theme.latoFamily
import kotlin.random.Random

@Composable
fun CustomAddContent(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    viewModel: MainViewModel,
    modifier: Modifier
) {
    var productName by remember {
        mutableStateOf("")
    }
    var productQuantity by remember {
        mutableStateOf("")
    }

    val localContext = LocalContext.current

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        },
    ) {
        Card(
            modifier = modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(375.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Ajoute ton produit",
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal
                    )
                }

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = modifier.fillMaxWidth()
                )
                CustomDropdownMenu(
                    viewModel = viewModel,
                    modifier = modifier
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                ) {
                    OutlinedTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = {
                            Text(
                                text = "Nom de l'article"
                            )
                        },
                        textStyle = TextStyle(
                            fontStyle = FontStyle.Normal,
                            color = Color.Black
                        )
                    )

                    OutlinedTextField(
                        value = productQuantity,
                        label = {
                            Text(
                                text = "Quantité"
                            )
                        },
                        onValueChange = { productQuantity = it },
                        textStyle = TextStyle(
                            fontStyle = FontStyle.Normal,
                            color = Color.Black
                        )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Button(
                            onClick = {
                                if(productName.isNotEmpty() && productQuantity.isNotEmpty()){
                                    viewModel.addNewProduct(
                                        Product(
                                            id = Random.nextInt(),
                                            name = productName,
                                            timestamp = System.currentTimeMillis()
                                        )
                                    )
                                    onConfirmation()
                                } else {
                                    Toast.makeText(
                                        localContext, "Il manque quelque chose", Toast.LENGTH_SHORT
                                    ).show()
                                }

                            },
                            shape = RectangleShape,
                            colors = ButtonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White,
                                disabledContainerColor = Color.DarkGray,
                                disabledContentColor = Color.Black
                            ),
                            content = {
                                Column(
                                    horizontalAlignment = Alignment.End,
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    Text(
                                        text = "OK",
                                        fontSize = 20.sp,
                                        fontFamily = latoFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        )
                    }

                }

            }
        }
    }
}