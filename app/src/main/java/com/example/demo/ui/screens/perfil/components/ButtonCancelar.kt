package com.example.demo.ui.screens.perfil.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.SecondaryButton

// Adapta el botón secundario global de ui/components para cancelar la operación
@Composable
fun ButtonCancelar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SecondaryButton(
        text = "CANCELAR",
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    )
}
