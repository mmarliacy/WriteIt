package com.projects.writeit.feature_product.presentation.list_product.components.bottom_app_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable représentant un menu déroulant contenant des options de tri
 * impactant directement la liste de produits.
 *
 * Chaque option est reliée à une logique (bientôt intégrée dans `ProductViewModel`)
 * influençant l'ordonnancement et l'affichage des produits dans la liste.
 *
 * La menu déroulant s'affiche via le bouton "SortButton" de `BottomAppBar`
 * et se ferme soit par la fonction onDismiss() ou en cliquant sur une option.
 *
 * @param modifier Permet d'ajuster l’apparence depuis l’extérieur si nécessaire.
 * @param expanded ViewModel injecté pour gérer l’état et les événements.
 * @param onDismiss Fonction appelée lors de la fermeture du dialogue.
 */
@Composable
fun SortDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismiss: () -> Unit
) {
    Box(
        modifier = modifier.padding(16.dp)
    ) {

        // -> Menu déroulant qui contient les options de tri.
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss
        ) {

            // -> Composable qui permet de gérer l'item : "Trier par nom"
            DropdownMenuItem(
                text = { Text("Par nom") },
                onClick = {},
                enabled = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Title,
                        contentDescription = "SortByName"
                    )
                }
            )

            // -> Composable qui permet de gérer l'item : "Trier par date"
            DropdownMenuItem(
                text = { Text("Par Date") },
                onClick = {},
                enabled = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "SortByDate"
                    )
                }
            )

            // -> Composable qui permet de gérer l'item : "Trier par catégorie"
            DropdownMenuItem(
                text = { Text("Par catégorie") },
                onClick = {},
                enabled = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Category,
                        contentDescription = "SortByCategory"
                    )
                }
            )
        }
    }
}