package com.example.demo.ui.screens.evolucion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.dominio.model.EstadisticasInterrupcionSueno
import com.example.demo.dominio.model.RegistroSueno
import java.util.Locale

@Composable
fun SleepTrendChart(
    interrupcionSueno: EstadisticasInterrupcionSueno?,
    registrosSueno: List<RegistroSueno>,
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
                text = "Tendencia y Calidad de Sueño",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4C0099)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (interrupcionSueno == null || interrupcionSueno.nochesTotales == 0) {
                InsufficientDataMessage()
            } else {
                val duracionPromedioMin = if (registrosSueno.isNotEmpty()) {
                    registrosSueno.map { it.duracionMinutos }.average()
                } else 0.0
                val duracionHoras = remember(duracionPromedioMin) {
                    String.format(Locale.getDefault(), "%.1f", duracionPromedioMin / 60.0)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFDCD7FF))
                        .padding(14.dp)
                ) {
                    Text(
                        text = "Duración de sueño promedio: $duracionHoras hrs",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4C0099)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val pctSin = interrupcionSueno.porcentajeSinInterrupcion.toFloat()
                    val sweepAngleFilled = (pctSin / 100f) * 360f

                    Box(
                        modifier = Modifier.size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(110.dp)) {
                            val strokeWidth = 14.dp.toPx()
                            drawArc(
                                color = Color(0xFFFF751F),
                                startAngle = 0f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                            drawArc(
                                color = Color(0xFF7162FB),
                                startAngle = -90f,
                                sweepAngle = sweepAngleFilled,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                        }

                        val pctText = remember(pctSin) {
                            String.format(Locale.getDefault(), "%.0f%%", pctSin)
                        }
                        Text(
                            text = pctText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4C0099)
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color(0xFF7162FB))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val pctSinText = remember(interrupcionSueno.porcentajeSinInterrupcion) {
                                String.format(Locale.getDefault(), "%.1f%%", interrupcionSueno.porcentajeSinInterrupcion)
                            }
                            Text(
                                text = "Sin interrupciones ($pctSinText)",
                                fontSize = 13.sp,
                                color = Color(0xFF323235)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color(0xFFFF751F))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val pctConText = remember(interrupcionSueno.porcentajeConInterrupcion) {
                                String.format(Locale.getDefault(), "%.1f%%", interrupcionSueno.porcentajeConInterrupcion)
                            }
                            Text(
                                text = "Con interrupciones ($pctConText)",
                                fontSize = 13.sp,
                                color = Color(0xFF323235)
                            )
                        }
                    }
                }
            }
        }
    }
}
