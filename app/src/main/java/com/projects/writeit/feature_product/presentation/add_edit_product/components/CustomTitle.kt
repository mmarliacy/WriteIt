package com.projects.writeit.feature_product.presentation.add_edit_product.components


import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.projects.writeit.ui.theme.latoFamily

/**
 * Titre personnalisable qui est utilisé pour l'en-tête du dialogue d'ajout de produit.
 *
 * @param fontFamily, est fixé par défaut car c'est la police d'écriture choisie.
 */
@Composable
fun CustomTitle(
    title: String,
    fontFamily: FontFamily = latoFamily,
    size: TextUnit,
    fontStyle: FontStyle,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            fontSize = size,
            fontStyle = fontStyle
        )
    }
}
