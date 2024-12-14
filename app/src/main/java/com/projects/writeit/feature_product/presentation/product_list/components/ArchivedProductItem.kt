package com.projects.writeit.feature_product.presentation.product_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projects.writeit.feature_product.domain.model.Product
import com.projects.writeit.feature_product.presentation.product_list.MainViewModel

@Composable
fun ArchivedProductItem(mainViewModel: MainViewModel = viewModel(), product: Product) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(5.dp)
            .clickable {
                mainViewModel.cancelDeletion(product)
            },
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            Modifier.background(Color.DarkGray).padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.name,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )

            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "",
                modifier = Modifier.size(35.dp),
                tint = Color.White
            )
        }
    }

}