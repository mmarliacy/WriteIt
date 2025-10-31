package com.projects.writeit.feature_product.presentation.add_edit_product.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
        Surface(
            modifier = modifier
                .height(56.dp),
            color = Color.White,
            shape = RoundedCornerShape(30),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
            shadowElevation = 0.dp
        ) {
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart // 🔹 Centre verticalement ton texte
            ){
                BasicTextField(
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
                    decorationBox =
                    { innerTextField ->
                        if (isHintVisible) {
                            Text(
                                text = hint,
                                style = textStyle.copy(color = Color.Gray),
                                color = darkPrimaryColor
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }
}