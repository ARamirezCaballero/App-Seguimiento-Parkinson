package com.example.demo.ui.screens.registroestadomotor.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.TopBackHeader

// Header con flecha para volver y separador encima de content
@Composable
fun EstadoMotorHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopBackHeader(
        onBackClick = onBackClick,
        modifier = modifier
    )
}
