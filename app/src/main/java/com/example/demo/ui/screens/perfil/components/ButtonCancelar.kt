package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

//Texto blanco centrado "GUARDAR PERFIL" en el boton color purpura #7162FB solo los bordes, relleno blanco
//Ancho coompleto del botn con borde redondeados y area tactil minima de 14mm
@Composable
fun ButtonCancelar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Color(0xFF7162FB)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF7162FB)
        )
    ) {
        Text(
            text = "CANCELAR",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7162FB)
        )
    }
}
