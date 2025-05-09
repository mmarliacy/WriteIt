package com.projects.writeit.feature_product.presentation.list_product.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.surfaceLight

@Composable
fun ProductItem(
    product: Product,
    onDeleteClick: () -> Unit
) {
    Surface(
        color = surfaceLight,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                onDeleteClick()
            },
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(10.dp)

        ) {
            Text(
                text = "${product.quantity} x ",
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
    }
}

