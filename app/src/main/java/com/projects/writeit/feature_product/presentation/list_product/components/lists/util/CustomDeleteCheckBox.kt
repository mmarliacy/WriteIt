package com.projects.writeit.feature_product.presentation.list_product.components.lists.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projects.writeit.ui.theme.ErrorRed
import com.projects.writeit.ui.theme.White

/**
 * Composable représentant une case à cocher utilisée en mode suppression d'article.
 *
 *
 * @param deleteChecked Indique si la case est actuellement cochée (activée).
 * @param onDeleteCheckedChange Méthode appelée lorsque l'utilisateur coche ou décoche la case.
 * @param modifier Modificateur optionnel pour personnaliser l'apparence ou le comportement du composable.
 */
@Composable
fun CustomDeleteCheckBox(
    deleteChecked: Boolean,
    onDeleteCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(2.dp, ErrorRed, RoundedCornerShape(3.dp))
            .height(20.dp)
            .width(20.dp)
            .drawBehind {
                val cornerRadius = 3.dp.toPx()
                drawRoundRect(
                    color = White,
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
            .clip(
                RoundedCornerShape(5.dp)
            )
            // Au clic sur la case à cocher, le Callback est déclenché.
            .clickable {
                onDeleteCheckedChange(!deleteChecked)
            }
    ) {

        Column (modifier = Modifier.align(Alignment.Center)){
                AnimatedVisibility(
                    visible = deleteChecked,
                    modifier = modifier,
                    enter = scaleIn(initialScale = 0.1f),
                    exit = shrinkOut(shrinkTowards = Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        tint = ErrorRed,
                        contentDescription = "Icône de suppression"
                    )
                }
        }

    }


}

@Preview
@Composable
fun SeeCheckbox(){
    CustomDeleteCheckBox(
        deleteChecked = true,
        onDeleteCheckedChange = { },
        modifier = Modifier
    )
}