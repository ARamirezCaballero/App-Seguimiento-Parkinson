package com.example.demo.ui.screens.evolucion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.dominio.model.DistribucionEstadoMotor
import com.example.demo.dominio.model.enums.EstadoMotor
import java.util.Locale

@Composable
fun MotorStateDistributionChart(
    distribucion: DistribucionEstadoMotor?,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Distribución del Estado Motor",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4C0099)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (distribucion == null || distribucion.totalRegistros == 0) {
                InsufficientDataMessage()
            } else {
                val pctMap = distribucion.porcentajePorEstado
                val items = mutableListOf<Pair<String, Double>>()

                items.add("ON" to (pctMap[EstadoMotor.ON] ?: 0.0))
                items.add("OFF" to (pctMap[EstadoMotor.OFF] ?: 0.0))
                items.add("Discinesia" to (pctMap[EstadoMotor.Discinesia] ?: 0.0))
                val pctDorm = pctMap[EstadoMotor.Dormido] ?: 0.0
                if (pctDorm > 0) {
                    items.add("Dormido" to pctDorm)
                }
                items.add("Sin info" to distribucion.porcentajeSinInformacion)

                items.forEach { (nombre, porcentaje) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = nombre,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF323235),
                            modifier = Modifier.width(70.dp)
                        )

                        val pctClamped = (porcentaje / 100.0).coerceIn(0.0, 1.0).toFloat()

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFFF751F))
                        ) {
                            if (pctClamped > 0f) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(pctClamped)
                                        .height(16.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF7162FB))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        val pctText = remember(porcentaje) {
                            String.format(Locale.getDefault(), "%.1f%%", porcentaje)
                        }
                        Text(
                            text = pctText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4C0099),
                            modifier = Modifier.width(50.dp)
                        )
                    }
                }
            }
        }
    }
}
