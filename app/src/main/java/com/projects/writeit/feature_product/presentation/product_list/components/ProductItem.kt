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
fun ProductItem(mainViewModel: MainViewModel = viewModel(), product: Product) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                mainViewModel.deleteProduct(product)
            },
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(Modifier.padding(20.dp)) {
            Text(
                text = product.name,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

