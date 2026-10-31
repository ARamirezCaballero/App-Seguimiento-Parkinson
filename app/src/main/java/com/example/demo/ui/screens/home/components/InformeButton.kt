package com.example.demo.ui.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun InformeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    //boton con Fondo blanco con texto "Informe" color #4C0099 poppins semibold centrado
    //Al pusarlo navega a InformeScreen
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF4C0099)
        )
    ) {
        Text(
            text = "Informe",
            color = Color(0xFF4C0099),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}
