package com.projects.writeit.feature_product.presentation.list_product.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.ui.theme.BlueNeutral
import com.projects.writeit.ui.theme.BluePrimary
import com.projects.writeit.ui.theme.SoftPink
import com.projects.writeit.ui.theme.White
import com.projects.writeit.ui.theme.darkAccentColor
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.surfaceLight
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
 * @param item Le produit à afficher.
 * @param isDeletionModeActive Active ou non l'affichage de la case à cocher.
 * @param checked Indique si le produit est actuellement coché pour suppression.
 * @param putInCaddyClick Action exécutée lors d'un clic simple sur l'icône "mettre dans le caddy".
 * @param onClickItem Action exécutée lors d'un appui sur l'item.
 * @param onCheckedChange Action déclenchée lorsqu’on coche ou décoche l’item.
 */
@Composable
fun WishItem(
    item : Item,
    isDeletionModeActive: Boolean,
    checked: Boolean,
    putInCaddyClick: () -> Unit, // Vérifier que la méthode correspond toujours a celle qui envoie l'article dans l'autre liste.
    onClickItem: () -> Unit, // On met l'item à jour -> Logique à modifier
    onCheckedChange: (Boolean) -> Unit
) {

    // -> Calcule le prix total du produit (quantité × prix unitaire).
    // -> Le résultat est arrondi à 2 décimales puis converti en String pour l'affichage dans l'UI.
    val prixTotal = BigDecimal(item.price * item.quantity)
        .setScale(2, RoundingMode.HALF_UP)
        .toString()

    Surface(
        color = surfaceLight,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 2.dp)
            .clickable (
                onClick = {onClickItem()}
            ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp)

            ) {
                // -> Quand le mode "Suppression" est actif une case à cocher apparaît à gauche de l'item.
                if (isDeletionModeActive) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = {
                                onCheckedChange(it)
                            },
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = White,
                                checkedColor = darkAccentColor
                            ),
                            // Utiliser pour supprimer le padding autour du checkbox
                            modifier = Modifier.size(20.dp)
                        )
                } else {
                    // -> Bouton qui sert à mettre l'article dans le caddy => TODO()
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCartCheckout,
                        tint = SoftPink,
                        contentDescription = "Mettre dans le caddy",
                        modifier = Modifier.clickable { putInCaddyClick() }.size(25.dp)
                    )
                }
                // -> Quantité du produit.
                Text(
                    text = "${item.quantity}",
                    color = BlueNeutral,
                    fontSize = 15.5.sp,
                    fontFamily = latoFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp)
                )

                // -> Nom du produit.
                Text(
                    text = item.name,
                    fontSize = 15.5.sp,
                    fontFamily = latoFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = BluePrimary
            ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(
                    start = 10.dp,
                    top = 10.dp,
                    end = 10.dp
                ).width(100.dp)
            ) {
                    // -> Prix global du produit = Prix unitaire x quantité.
                    Text(
                        text = "$prixTotal €",
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = latoFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                // -> Prix unitaire du produit.
                Text(
                    text = "${item.price} € l'unité",
                    fontSize = 12.sp,
                    color = BlueNeutral
                )
                }
            }
        }
    }
}

@Preview
@Composable
fun ShowMeWishItem(){
    val fakeItem = Item(
        id = 1,
        name = "Tablette de Chocolat",
        quantity = 2,
        price = 2.50,
        category = "Petit-Déjeuner",
        timestamp = 1234,
        isInTheCaddy = true
    )
    WishItem(
        item = fakeItem,
        isDeletionModeActive = false,
        checked = false,
        putInCaddyClick = { },
        onClickItem = { },
        onCheckedChange = { }
    ) 
}

