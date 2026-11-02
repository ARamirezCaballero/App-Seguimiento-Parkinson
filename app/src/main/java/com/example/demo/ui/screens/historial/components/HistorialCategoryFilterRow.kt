package com.example.demo.ui.screens.historial.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.dominio.model.enums.CategoriaHistorial

@Composable
fun HistorialCategoryFilterRow(
    categoriaSeleccionada: CategoriaHistorial,
    onCategoriaSelected: (CategoriaHistorial) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoriaHistorial.entries.forEach { cat ->
            val isSelected = cat == categoriaSeleccionada
            FilterChip(
                selected = isSelected,
                onClick = { onCategoriaSelected(cat) },
                label = {
                    Text(
                        text = cat.label,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Default,
                        fontSize = 13.sp
                    )
                },
                modifier = Modifier.defaultMinSize(minHeight = 44.dp),
                shape = RoundedCornerShape(20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF4C0099),
                    selectedLabelColor = Color.White,
                    containerColor = Color(0xFFE5E0FF),
                    labelColor = Color(0xFF4C0099)
                )
            )
        }
    }
}
