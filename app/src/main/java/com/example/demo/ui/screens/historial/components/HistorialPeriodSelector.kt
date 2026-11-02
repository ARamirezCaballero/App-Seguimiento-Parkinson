package com.example.demo.ui.screens.historial.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun HistorialPeriodSelector(
    fechaInicio: LocalDate,
    fechaFin: LocalDate,
    onPeriodoSelected: (LocalDate, LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPeriodIndex by remember { mutableStateOf(0) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedPeriodIndex == 0,
            onClick = {
                selectedPeriodIndex = 0
                val fin = LocalDate.now()
                val inicio = fin.minusDays(7)
                onPeriodoSelected(inicio, fin)
            },
            label = {
                Text(
                    text = "Últimos 7 días",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    fontSize = 13.sp
                )
            },
            modifier = Modifier.defaultMinSize(minHeight = 44.dp),
            shape = RoundedCornerShape(20.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = Color(0xFF7162FB),
                selectedLabelColor = Color.White,
                containerColor = Color(0xFFF0ECFF),
                labelColor = Color(0xFF4C0099)
            )
        )

        FilterChip(
            selected = selectedPeriodIndex == 1,
            onClick = {
                selectedPeriodIndex = 1
                val fin = LocalDate.now()
                val inicio = fin.minusDays(30)
                onPeriodoSelected(inicio, fin)
            },
            label = {
                Text(
                    text = "Últimos 30 días",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    fontSize = 13.sp
                )
            },
            modifier = Modifier.defaultMinSize(minHeight = 44.dp),
            shape = RoundedCornerShape(20.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = Color(0xFF7162FB),
                selectedLabelColor = Color.White,
                containerColor = Color(0xFFF0ECFF),
                labelColor = Color(0xFF4C0099)
            )
        )
    }
}
