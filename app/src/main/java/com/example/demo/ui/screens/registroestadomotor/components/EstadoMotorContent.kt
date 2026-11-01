package com.example.demo.ui.screens.registroestadomotor.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.dominio.model.enums.EstadoMotor

// Tarjetas rectangulares y bordes redondeados con texto en poppins blanco bold
// las tarjetas deben tener suficiente espacio siendo este no menos de 14mm
// cada tarjeta debe contener el nombre de un enum EstadoMotor
// al apretar una de las tarjetas se abre un dialog
@Composable
fun EstadoMotorContent(
    onEstadoMotorClick: (EstadoMotor, Color) -> Unit,
    modifier: Modifier = Modifier
) {
    val tarjetas = listOf(
        Pair(EstadoMotor.ON, Color(0xFF95DA4B)),
        Pair(EstadoMotor.OFF, Color(0xFFEA6B66)),
        Pair(EstadoMotor.Discinesia, Color(0xFF7EA6E0)),
        Pair(EstadoMotor.Dormido, Color(0xFFFF8000))
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registro de Estado Motor",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = Color(0xFF4C0099),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            tarjetas.forEach { (estado, color) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp)
                        .clickable { onEstadoMotorClick(estado, color) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = color)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = estado.name,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Default,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
