package com.example.demo.ui.screens.registrotoma.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.TopBackHeader

// Header con flecha para atrás
@Composable
fun RegistroTomaHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopBackHeader(
        onBackClick = onBackClick,
        modifier = modifier
    )
}
