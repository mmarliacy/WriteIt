package com.projects.writeit.feature_product.presentation.list_product.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.writeit.feature_product.domain.model.Item
import com.projects.writeit.ui.theme.Black
import com.projects.writeit.ui.theme.latoFamily
import com.projects.writeit.ui.theme.surfaceLight

/**
 * Composable représentant l'item (le produit) archivé provenant de la liste active.
 * Il s'agit d'un bloc où seul le nom du produit apparaît.
 *
 * Accompagné d'une icône "+" dans le cas où on souhaiterait singulièrement
 * le ré-intégrer dans la liste active.
 *
 * @param pItem Il s'agit du produit archivé.
 * @param onRestoreClick on renvoie le produit vers la liste active de produits dans l'onglet "Liste".
 */
@Composable
fun CaddyItem (
    pItem: Item,
    onRestoreClick: () -> Unit
) {
    Surface(
        color = surfaceLight,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCartCheckout,
                    contentDescription = "Mettre dans le caddy"
                )
            Text(
                text = pItem.name,
                fontSize = 15.5.sp,
                fontFamily = latoFamily,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 10.dp)
            )
            // -> Icône "+" sur laquelle on clique par la fonction `clickable()`
            // -> pour sa ré-intégration dans la liste active.
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onRestoreClick()
                    },
                tint = Black
            )
        }
    }

}

@Preview
@Composable
fun ShowMeCaddyItem(){
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
        pItem = fakeItem,
        onRestoreClick = {}
    )
}