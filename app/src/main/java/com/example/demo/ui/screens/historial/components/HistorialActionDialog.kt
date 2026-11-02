package com.example.demo.ui.screens.historial.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.demo.dominio.model.ItemHistorial

@Composable
fun HistorialActionDialog(
    item: ItemHistorial,
    onEliminarClick: () -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onCancelarClick,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF7162FB)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = item.nombre,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Default,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Origen: ${item.origenDatos}",
                        color = Color(0xFFE5E0FF),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (item.origenDatos.contains("manual", ignoreCase = true)) {
                        Button(
                            onClick = onEliminarClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFFD32F2F)
                            )
                        ) {
                            Text(
                                text = "Eliminar Registro",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD32F2F),
                                fontFamily = FontFamily.Default,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        Text(
                            text = "Las métricas pasivas de Health Connect no se pueden eliminar desde la aplicación.",
                            color = Color.White,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            TextButton(
                onClick = onCancelarClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(0.9f)
                    .defaultMinSize(minHeight = 56.dp)
            ) {
                Text(
                    text = "Cancelar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    fontSize = 18.sp
                )
            }
        }
    }
}
