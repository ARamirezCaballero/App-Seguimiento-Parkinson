package com.example.demo.ui.screens.registrotoma.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.demo.ui.screens.registrotoma.MedicamentoConPautaItem

// Tarjetas rectangulares y bordes redondeados con color #7ea6e0 y texto en poppins blanco bold
// las tarjetas deben tener suficiente espacio siendo este no menos de 14mm
// cada tarjeta debe contener el nombre de cada medicamento registrado y su dosis general como "nombre" "dosis general"
// al apretar una de las tarjetas se abre un dialog de RegistroTomaDialog
@Composable
fun RegistroTomaContent(
    medicamentos: List<MedicamentoConPautaItem>,
    onMedicamentoClick: (MedicamentoConPautaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Registrar Toma de Medicación",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Color(0xFF4C0099),
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 4.dp),
                textAlign = TextAlign.Center
            )
        }

        if (medicamentos.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay medicamentos registrados",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            items(medicamentos) { item ->
                MedicamentoTarjetaItem(
                    item = item,
                    onClick = { onMedicamentoClick(item) }
                )
            }
        }
    }
}

@Composable
private fun MedicamentoTarjetaItem(
    item: MedicamentoConPautaItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp) // Al menos 14mm de espacio accesible
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF7EA6E0)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = item.displayNombreYDosis,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default
            )
        }
    }
}
