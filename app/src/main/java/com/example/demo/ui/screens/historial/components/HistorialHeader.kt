package com.example.demo.ui.screens.historial.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.TopBackHeader

@Composable
fun HistorialHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopBackHeader(
        onBackClick = onBackClick,
        title = "Historial",
        modifier = modifier
    )
}
