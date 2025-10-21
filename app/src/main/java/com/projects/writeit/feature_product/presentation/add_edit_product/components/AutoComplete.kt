package com.projects.writeit.feature_product.presentation.add_edit_product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.AddEditViewModel
import com.projects.writeit.feature_product.presentation.add_edit_product.util.AddEditItemEvent

/**
 * Text field personnalisé qui affiche un menu déroulant en fonction des entrées utilisateur.
 *
 * @param onFocusChange déclenche un évènement suite à la perte du focus.
 * @param editViewModel connecte la liste et la gère en fonction des clics utilisateurs.
 */
@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    onFocusChange: (FocusState) -> Unit,
    editViewModel: AddEditViewModel = hiltViewModel()
) {
    // Text field neutre à l'initial
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    // Liste des suggestions filtrée depuis la liste de catégories en fonction
    // des entrées de l'utilisateur.
    val suggestions = editViewModel.suggestions.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
                editViewModel.onEvent(AddEditItemEvent.UpdateSuggestions(newValue.text))
            },
            placeholder = {
                Text("Ton article est...")
            },
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        if (suggestions.value.isNotEmpty() && textFieldValue.text.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .heightIn(200.dp)
                    .padding(18.dp)
            ) {
                items(suggestions.value) { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                textFieldValue = TextFieldValue(
                                    text = suggestion,
                                    selection = TextRange(suggestion.length)
                                )
                                editViewModel.onEvent(AddEditItemEvent.ClearSuggestions)
                                editViewModel.onEvent(AddEditItemEvent.SelectCategory(suggestion))
                            }
                            .padding(10.dp)
                    )

                }
            }
        }
    }
}