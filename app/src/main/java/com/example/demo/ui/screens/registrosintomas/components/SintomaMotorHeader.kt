package com.example.demo.ui.screens.registrosintomas.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.TopBackHeader

// header con flecha para ir atrás y separador encima de content
@Composable
fun SintomaMotorHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopBackHeader(
        onBackClick = onBackClick,
        modifier = modifier
    )
}
