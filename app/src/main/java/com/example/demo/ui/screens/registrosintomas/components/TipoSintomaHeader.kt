package com.example.demo.ui.screens.registrosintomas.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.TopBackHeader

// Header con flecha para ir atrás y separador encima de content
@Composable
fun TipoSintomaHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopBackHeader(
        onBackClick = onBackClick,
        modifier = modifier
    )
}
