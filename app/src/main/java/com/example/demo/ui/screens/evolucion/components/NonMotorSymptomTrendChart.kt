package com.example.demo.ui.screens.evolucion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.dominio.model.PuntoSintoma

@Composable
fun NonMotorSymptomTrendChart(
    sintomasNoMotores: List<PuntoSintoma>,
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
                text = "Tendencia de Síntomas No Motores",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4C0099)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (sintomasNoMotores.isEmpty()) {
                InsufficientDataMessage()
            } else {
                val sorted = sintomasNoMotores.sortedBy { it.fechaHora }

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    val width = size.width
                    val height = size.height
                    val maxSeverity = 4f
                    val lineColor = Color(0xFF6C5DEC)
                    val gridColor = Color(0xFFE0E0E0)

                    for (i in 0..4) {
                        val y = height - (i / maxSeverity) * height
                        drawLine(
                            color = gridColor,
                            start = Offset(0f, y),
                            end = Offset(width, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    if (sorted.size == 1) {
                        val p = sorted[0]
                        val cx = width / 2f
                        val cy = height - (p.severidad / maxSeverity) * height
                        drawCircle(
                            color = lineColor,
                            radius = 4.dp.toPx(),
                            center = Offset(cx, cy)
                        )
                    } else {
                        val path = Path()
                        val points = mutableListOf<Offset>()

                        for (idx in sorted.indices) {
                            val item = sorted[idx]
                            val x = (idx.toFloat() / (sorted.size - 1)) * width
                            val y = height - (item.severidad.toFloat() / maxSeverity) * height
                            points.add(Offset(x, y))

                            if (idx == 0) {
                                path.moveTo(x, y)
                            } else {
                                path.lineTo(x, y)
                            }
                        }

                        drawPath(
                            path = path,
                            color = lineColor,
                            style = Stroke(width = 2.dp.toPx())
                        )

                        points.forEach { point ->
                            drawCircle(
                                color = lineColor,
                                radius = 4.dp.toPx(),
                                center = point
                            )
                        }
                    }
                }
            }
        }
    }
}
