package com.projects.writeit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = White,
    secondary = BlueNeutral,
    onSecondary = White,
    background = White,
    onBackground = BluePrimary,
    surface = White,
    onSurface = BluePrimary,
    error = ErrorRed,
    onError = White
)

@Composable
fun WriteItTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
