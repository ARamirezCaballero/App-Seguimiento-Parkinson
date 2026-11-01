package com.example.demo.ui.screens.perfil.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.PrimaryButton

// Adapta el botón principal global de ui/components para guardar el perfil
@Composable
fun ButtonGuardarPerfil(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    PrimaryButton(
        text = "GUARDAR PERFIL",
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    )
}
