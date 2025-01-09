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
import com.projects.writeit.ui.theme.WriteItCustomTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WriteItCustomTheme{
                Main(viewModel, modifier = Modifier)
            }
        }
    }
}



