package com.projects.writeit.feature_product.presentation.list_product.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.ui.theme.darkAccentColor
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.surfaceLight
import com.projects.writeit.ui.theme.whiteColor
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun ProductItem(
    product: Product,
    isDeletionModeActive: Boolean,
    checked : Boolean,
    onClickItem: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {

    val prixTotal = BigDecimal(product.price * product.quantity)
        .setScale(2, RoundingMode.HALF_UP)
        .toString()

    Surface(
        color = surfaceLight,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                onClickItem()
            },
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isDeletionModeActive) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        onCheckedChange(it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = whiteColor,
                        checkedColor = darkAccentColor
                    )
                    )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 10.dp, end = 10.dp)

            ) {
                Text(
                    text = "${product.quantity}",
                    color = darkAccentColor,
                    fontSize = 15.5.sp,
                    fontFamily = latoFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp)
                )

                Text(
                    text = product.name,
                    fontSize = 15.5.sp,
                    fontFamily = latoFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(top = 5.dp, end = 10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(5.dp),
                    color = darkAccentColor
                ) {
                    Text(
                        text = "$prixTotal €",
                        color = Color.White,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
                Text(
                    text = "Unité : ${product.price} €",
                    fontSize = 12.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }


        }
    }
}

