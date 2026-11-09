package com.example.demo.ui.screens.informe.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReportContentSummary(
    modifier: Modifier = Modifier,
) {
    val elementosIncluidos = listOf(
        "Datos del paciente",
        "Adherencia farmacológica",
        "Estado motor y fluctuaciones",
        "Registro y severidad de síntomas",
        "Resumen de sueño"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = "El informe incluirá:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF323235)
        )

        Spacer(modifier = Modifier.height(12.dp))

        elementosIncluidos.forEach { elemento ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✓",
                    color = Color(0xFF4C0099),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = elemento,
                    fontSize = 15.sp,
                    color = Color(0xFF4A4A4A)
                )
            }
        }
    }
}
