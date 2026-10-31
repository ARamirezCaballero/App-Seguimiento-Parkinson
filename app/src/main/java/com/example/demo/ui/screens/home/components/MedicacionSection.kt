package com.example.demo.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MedicationSection(
    modifier: Modifier = Modifier,
    onRegistrarTomaClick: () -> Unit = {},
    onAddMedicamentoClick: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        //Titulo "MEDICACION" con tipografia Poppins en bold con color #4C0099 aineado a la izquierda debajo de seccion QuickAccesCard
        Text(
            text = "MEDICACIÓN",
            color = Color(0xFF4C0099),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        //Cuadricula de dos columnas
        //Botones cuadrados alineados respectivamente con terjetas de la seccion QuickAccessCard
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RegisterMedicationIntakeButton(
                modifier = Modifier.weight(1f),
                onClick = onRegistrarTomaClick
            )
            AddMedicationButton(
                modifier = Modifier.weight(1f),
                onClick = onAddMedicamentoClick
            )
        }
    }
}
