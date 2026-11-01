package com.example.demo.ui.screens.registrosintomas.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.TopBackHeader

// header con flecha para volver y separador arriba del content
@Composable
fun SintomaNoMotorHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopBackHeader(
        onBackClick = onBackClick,
        modifier = modifier
    )
}
