package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//Error de validacion para completar campos obligatorios
//Error de persistencia para el error de guardaod de perfil
//mensaje de  guardado exitoso
@Composable
fun ProfileStatusMessage(
    modifier: Modifier = Modifier,
    mensajeError: String? = null,
    mensajeExito: String? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (mensajeError != null) {
            //Error de persistencia / validación
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.errorContainer,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
        } else if (mensajeExito != null) {
            //mensaje de  guardado exitoso
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFE8F5E9),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = mensajeExito,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}
