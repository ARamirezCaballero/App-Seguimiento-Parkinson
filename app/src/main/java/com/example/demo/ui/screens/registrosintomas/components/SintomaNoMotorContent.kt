package com.example.demo.ui.screens.registrosintomas.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.example.demo.dominio.model.enums.TipoSintoma

// Titulo "Síntomas No Motores"
// grid de 2 columnas
// 2 tarjetas en una fila, 2 en la fila de abajo y una en la fila de abajo
// Tarjetas con el nombre de sintomas no motores del Enum TipoSintoma
// texto de tarjetas Poppins blanco bold
// Tarjeta 1 borde redondeado no menos de 14mm con color #FC836B
// Tarjeta 2 borde redondeado no menos de 14mm con color #7162FB
// Tarjeta 3 borde redondeado no menos de 14mm con color #95DA4B
// Tarjeta 4 borde redondeado no menos de 14mm con color #31C2FC
// Tarjeta 5 borde redondeado no menos de 14mm con color #FF1FA9
@Composable
fun SintomaNoMotorContent(
    onSintomaClick: (TipoSintoma, Color) -> Unit,
    modifier: Modifier = Modifier
) {
    val noMotores = listOf(
        Pair(TipoSintoma.Fatiga, Color(0xFFFC836B)),
        Pair(TipoSintoma.Dolor_calambre_distonia, Color(0xFF7162FB)),
        Pair(TipoSintoma.Gastrointestinales, Color(0xFF95DA4B)),
        Pair(TipoSintoma.Estado_animo, Color(0xFF31C2FC)),
        Pair(TipoSintoma.Autonomicos, Color(0xFFFF1FA9))
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Síntomas No Motores",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = Color(0xFF4C0099),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(noMotores.size) { index ->
                val (sintoma, color) = noMotores[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 80.dp)
                        .clickable { onSintomaClick(sintoma, color) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = color)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 80.dp)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = sintoma.titulo,
                            color = Color.White,
                            fontSize = 15.sp,
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
