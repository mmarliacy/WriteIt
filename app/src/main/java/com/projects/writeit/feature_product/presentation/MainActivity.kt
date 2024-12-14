package com.projects.writeit.feature_product.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.projects.writeit.feature_product.presentation.product_list.Main
import com.projects.writeit.feature_product.presentation.product_list.MainViewModel
import com.projects.writeit.ui.theme.WriteItTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WriteItTheme {
                Main(viewModel, modifier = Modifier)
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    val viewModel by remember {
        mutableStateOf(MainViewModel())
    }
Main(viewModel,modifier = Modifier)
}



