package com.projects.writeit.feature_product.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditScreen
import com.projects.writeit.feature_product.presentation.list_product.ProductsScreen
import com.projects.writeit.feature_product.presentation.util.Screen
import com.projects.writeit.ui.theme.WriteItTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WriteItTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ProductsScreen.route
                    ) {
                        composable(route = Screen.ProductsScreen.route) {
                            ProductsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditProductScreen.route + "?productId={productId}",
                            arguments = listOf(
                                navArgument(
                                    name = "productId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditScreen(
                                navController = navController
                            )
                        }
                    }

                }

            }
        }
    }
}



