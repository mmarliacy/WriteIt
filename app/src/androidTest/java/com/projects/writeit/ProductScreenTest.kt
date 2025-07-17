package com.projects.writeit

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.projects.writeit.feature_product.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ProductScreenTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainScreenIsVisible() {
        // Vérifie si le texte "Ajouter un produit" est visible
        composeTestRule.onNodeWithText("Ma liste de courses")
            .assertIsDisplayed()
    }

    @Test
    fun insertNewProduct() {
        // Focus Requester à tester dans AddEdit Dialog
        // Pour focus le champ de texte

        // Vérifie si le texte "Ajouter un produit" est visible
        composeTestRule.onNodeWithText("Ma liste de courses")
            .assertIsDisplayed()

        // Ouvre le dialogue
        composeTestRule.onNodeWithContentDescription("Open Add dialog").performClick()

        // Remplir le formulaire
        composeTestRule.onNodeWithTag("nameText")
            .performTextInput("Quinoa")

        composeTestRule.onNodeWithTag("quantityText")
            .performTextInput("1")

        composeTestRule.onNodeWithTag("priceText")
            .performTextInput("1.50")

        // Valider le formulaire
        composeTestRule.onNodeWithText("OK").performClick()
        // Vérifier que l'élément apparaît sur l'écran
        composeTestRule.onNodeWithText("Quinoa").assertIsDisplayed()
    }
}