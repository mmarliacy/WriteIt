package com.projects.writeit

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.writeit.ui.theme.WriteItTheme

class MainActivity : ComponentActivity() {

    private var shopList = ShopList.list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WriteItTheme {
                    ShopList(
                        list = shopList,
                        modifier = Modifier
                    )
            }
        }
    }
}


@Composable
fun ProductItem(product: String, modifier: Modifier) {
    Surface(modifier = modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp), shadowElevation = 1.dp, shape = RoundedCornerShape(10.dp)){
        Row (modifier.padding(20.dp)){
            Text(
                text = product,
                fontSize = 20.sp,
                modifier = modifier.padding(start = 10.dp)
            )
        }
    }

}

@Composable
fun ShopList(list: List<String>, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars).fillMaxSize()
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            items(list) { product ->
                ProductItem(product, modifier)
            }
        }
        Box(contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(
                containerColor = Color.Black,
                contentColor = Color.White,
                onClick = { AddProduct() }
            ) {
                Icon(Icons.Filled.Add, "Add floating action button")
            }
        }

    }
}


fun AddProduct() {
    TODO("Not yet implemented")
}


@Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        WriteItTheme {
            ProductItem("ok", modifier = Modifier)
        }
    }