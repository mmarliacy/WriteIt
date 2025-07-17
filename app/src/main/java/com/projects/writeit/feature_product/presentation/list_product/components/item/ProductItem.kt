package com.projects.writeit.feature_product.presentation.list_product.components.item

import androidx.compose.foundation.combinedClickable
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

/**
 * Composable représentant un produit actif affiché dans la liste principale.
 *
 * Il est affiché sous forme de carte cliquable, avec les informations suivantes :
 * - le nom du produit
 * - la quantité
 * - le prix unitaire
 * - le prix total calculé (quantité × prix unitaire)
 *
 * Si le mode suppression est activé, une case à cocher apparaît à gauche de l'item.
 * Les actions sur l'item (clic simple, clic long, changement d'état de la case) sont hissées
 * pour permettre une gestion centralisée dans le ViewModel.
 *
 * @param product Le produit à afficher.
 * @param isDeletionModeActive Active ou non l'affichage de la case à cocher.
 * @param checked Indique si le produit est actuellement coché pour suppression.
 * @param onClickItem Action exécutée lors d'un clic simple sur l'item.
 * @param onLongClickItem Action exécutée lors d'un appui long sur l'item.
 * @param onCheckedChange Action déclenchée lorsqu’on coche ou décoche l’item.
 */
@Composable
fun ProductItem(
    product: Product,
    isDeletionModeActive: Boolean,
    checked: Boolean,
    onClickItem: () -> Unit,
    onLongClickItem: () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {

    // -> Calcule le prix total du produit (quantité × prix unitaire).
    // -> Le résultat est arrondi à 2 décimales puis converti en String pour l'affichage dans l'UI.
    val prixTotal = BigDecimal(product.price * product.quantity)
        .setScale(2, RoundingMode.HALF_UP)
        .toString()

    Surface(
        color = surfaceLight,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .combinedClickable(
                onClick = { onClickItem() },
                onLongClick = { onLongClickItem() }
            ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            // -> Quand le mode "Suppression" est actif une case à cocher apparaît à gauche de l'item.
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

                // -> Quantité du produit.
                Text(
                    text = "${product.quantity}",
                    color = darkAccentColor,
                    fontSize = 15.5.sp,
                    fontFamily = latoFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp)
                )

                // -> Nom du produit.
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
                    // -> Prix global du produit = Prix unitaire x quantité.
                    Text(
                        text = "$prixTotal €",
                        color = Color.White,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
                // -> Prix unitaire du produit.
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

