package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Agrupa acciones correspondientes a guardar o cancelar operacion
//contiene a BotonGuardarPerfil y BotonCancelar
@Composable
fun FormularioPerfilActions(
    onGuardarClick: () -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSaving: Boolean = false,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ButtonGuardarPerfil(
            onClick = onGuardarClick,
            enabled = !isSaving
        )
        ButtonCancelar(
            onClick = onCancelarClick,
            enabled = !isSaving
        )
    }
}
