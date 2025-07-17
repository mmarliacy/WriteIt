package com.projects.writeit.feature_product.presentation.add_edit_product.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.projects.writeit.ui.theme.darkPrimaryColor
import com.projects.writeit.ui.theme.latoFamily


/**
 * Champ de texte personnalisé sans bordure visible.
 *
 * Il affiche un texte d'aide (hint) quand le champ est vide et non sélectionné,
 * selon la valeur de [isHintVisible].
 *
 * Ce champ utilise un style défini et réagit aux changements de focus et de texte.
 **/

@Composable
fun TransparentTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(
        fontFamily = latoFamily,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        fontStyle = FontStyle.Normal
    ),
    keyboardOptions: (KeyboardOptions),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    isError: Boolean = false,
    supportingErrorText : String? = null
) {
    Box(
        modifier = modifier
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                },
            isError = isError,
            supportingText = {
                if (supportingErrorText != null) {
                    Text(
                        text = supportingErrorText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.White,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                disabledIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            readOnly = false,
            enabled = true
        )
        if(isHintVisible){
            Text(
                text = hint,
                style = textStyle,
                color = darkPrimaryColor
            )
        }
    }
}