package com.example.demo.ui.screens.perfil.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.demo.ui.components.FormActionButtons

// Agrupa las acciones del perfil utilizando el componente global reutilizable FormActionButtons
@Composable
fun FormularioPerfilActions(
    onGuardarClick: () -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSaving: Boolean = false,
) {
    FormActionButtons(
        textoGuardar = "GUARDAR PERFIL",
        textoCancelar = "CANCELAR",
        onGuardarClick = onGuardarClick,
        onCancelarClick = onCancelarClick,
        modifier = modifier,
        enabled = !isSaving
    )
}
