package com.example.demo.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuickAccessCard(
    onRegistrarSintomaClick: () -> Unit = {},
    onRegistrarEstadoMotorClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    // La distribucion es de dos columnas con tarjetas de dimensiones cuadradas
    //Espaciado uniforme entre tarjetas y bordes redondeados
    // Mantiene anchura misma para todas las tarjetas
    // Se ubica debajo del titulo
    //Tarjetas para "Registrar sintoma" y "Registrar Estado Motor"
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RegisterSymptomsCard(
            modifier = Modifier.weight(1f),
            onClick = onRegistrarSintomaClick
        )
        RegisterMotorStateCard(
            modifier = Modifier.weight(1f),
            onClick = onRegistrarEstadoMotorClick
        )
    }
}
