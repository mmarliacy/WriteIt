package com.projects.writeit.feature_product.presentation.list_product.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.ui.theme.BlueLight
import com.projects.writeit.ui.theme.BlueNeutral
import com.projects.writeit.ui.theme.BluePrimary
import com.projects.writeit.ui.theme.SoftBlue
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.surfaceLight
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Composable représentant l'item (le produit) archivé provenant de la liste active.
 * Il s'agit d'un bloc où seul le nom du produit apparaît.
 *
 * Accompagné d'une icône "+" dans le cas où on souhaiterait singulièrement
 * le ré-intégrer dans la liste active.
 *
 * @param item Il s'agit du produit archivé.
 * @param onRestoreClick on renvoie le produit vers la liste active de produits dans l'onglet "Liste".
 */
@Composable
fun CaddyItem(
    item: Item,
    onRestoreClick: () -> Unit
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
            .padding(start = 5.dp, end = 5.dp)
            .clickable(
                onClick = {
                    // Au clic, on peut modifier l'article depuis le caddy
                }
            ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp)
            ) {
                IconButton(
                    onClick = {
                        // Au clic, l'article revient dans la liste prévisionnelle.
                        onRestoreClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCartCheckout,
                        contentDescription = "Mettre dans le caddy",
                        tint = SoftBlue,
                        modifier = Modifier.graphicsLayer {
                            scaleX = -1f // effet miroir vertical sur l'icône
                        }
                    )
                }
                // -> Quantité de l'article.
                Text(
                    text = "${item.quantity}",
                    color = BlueNeutral,
                    fontSize = 15.5.sp,
                    fontFamily = latoFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp)
                )

                // -> Nom de l'article.
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
                color = BlueLight
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
                        color = BluePrimary,
                        style = TextStyle(
                            fontFamily = latoFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // -> Prix unitaire du produit.
                    Text(
                        text = "${item.price} € l'unité",
                        fontSize = 12.sp,
                        color = SoftBlue
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun ShowMeCaddyItem() {
    val fakeItem = Item(
        id = 1,
        name = "Tablette de Chocolat",
        quantity = 2,
        price = 2.50,
        category = "Petit-Déjeuner",
        timestamp = 1234,
        isInTheCaddy = true
    )

    CaddyItem(
        item = fakeItem,
        onRestoreClick = {}
    )
}