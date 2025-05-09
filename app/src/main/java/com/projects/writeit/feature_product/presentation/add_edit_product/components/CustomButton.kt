package com.projects.writeit.feature_product.presentation.add_edit_product.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.writeit.ui.theme.latoFamily

@Composable
fun CustomButton(
    onSaveClick: () -> Unit
) {
    Box {
        Button(
            onClick = onSaveClick,
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