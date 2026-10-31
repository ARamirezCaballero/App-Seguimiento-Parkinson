package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

//Texto blanco centrado "GUARDAR PERFIL" en el boton color purpura #7162FB
//Ancho coompleto del botn con borde redondeados y area tactil minima de 14mm
@Composable
fun ButtonGuardarPerfil(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7162FB),
            contentColor = Color.White
        )
    ) {
        Text(
            text = "GUARDAR PERFIL",
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
