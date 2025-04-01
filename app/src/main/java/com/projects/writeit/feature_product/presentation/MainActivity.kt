package com.projects.writeit.feature_product.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import com.projects.writeit.feature_product.presentation.products.Main
import com.projects.writeit.feature_product.presentation.products.MainViewModel
import com.projects.writeit.ui.theme.WriteItTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WriteItTheme{
                Main(viewModel, modifier = Modifier)
            }
        }
    }
}



