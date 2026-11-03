package com.example.demo.ui.screens.historial.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.dominio.model.ItemHistorial
import java.time.format.DateTimeFormatter

@Composable
fun HistorialItemCard(
    item: ItemHistorial,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val fechaHoraFormatted = item.fechaHora.format(DateTimeFormatter.ofPattern("HH:mm 'hs -' dd/MM/yyyy"))

    Card(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFDCD8FE)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            //Hora del registro
            Text(
                text = fechaHoraFormatted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Default,
                color = Color(0xFF4C0099)
            )

            //Nombre del registro
            Text(
                text = item.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Color(0xFF4C0099)
            )

            if (item.detalles.isNotBlank()) {
                Text(
                    text = item.detalles,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default,
                    color = Color(0xFF4C0099)
                )
            }

            // Etiqueta explicita de origen de datos
            Text(
                text = "Origen: ${item.origenDatos}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Default,
                color = Color(0xFF6B5ECE)
            )
        }
    }
}
