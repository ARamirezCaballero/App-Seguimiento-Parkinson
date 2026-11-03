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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.TextStyle
import com.example.demo.dominio.model.enums.TipoRegistroHistorial
import com.example.demo.ui.screens.historial.HistorialUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.example.demo.ui.components.DefaultSeverityOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun HistorialActionDialog(
    item: ItemHistorial,
    uiState: HistorialUiState,
    onEliminarClick: () -> Unit,
    onCancelarClick: () -> Unit,
    onSeveridadChanged: (Int) -> Unit,
    onHoraChanged: (Int) -> Unit,
    onMinutoChanged: (Int) -> Unit,
    onGuardarClick: () -> Unit,
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
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF7162FB)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
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

                    if (item.origenDatos.contains("manual", ignoreCase = true)) {
                        
                        // --- INICIO EDICIÓN ---
                        if (item.tipoRegistro == TipoRegistroHistorial.SINTOMA) {
                            Text(
                                text = "Editar Severidad",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                DefaultSeverityOptions.forEach { option ->
                                    val isSelected = uiState.editingSeveridad == option.level
                                    Button(
                                        onClick = { onSeveridadChanged(option.level) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 48.dp)
                                            .border(
                                                width = 2.dp,
                                                color = if (isSelected) Color.White else Color.Transparent,
                                                shape = RoundedCornerShape(12.dp)
                                            ),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSelected) Color(0xFF4C0099) else Color.White,
                                            contentColor = if (isSelected) Color.White else Color.Black
                                        )
                                    ) {
                                        Text(
                                            text = option.displayText,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Default,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }

                        if (item.tipoRegistro == TipoRegistroHistorial.TOMA_MEDICACION || item.tipoRegistro == TipoRegistroHistorial.ESTADO_MOTOR) {
                            Text(
                                text = if (item.tipoRegistro == TipoRegistroHistorial.TOMA_MEDICACION) "Hora de la toma:" else "Hora del evento:",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = String.format("%02d", uiState.editingHora ?: 0),
                                    onValueChange = { input ->
                                        input.toIntOrNull()?.let { onHoraChanged(it) }
                                    },
                                    modifier = Modifier
                                        .width(80.dp)
                                        .defaultMinSize(minHeight = 48.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = Color.White,
                                        unfocusedBorderColor = Color.White,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )

                                Text(
                                    text = ":",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )

                                OutlinedTextField(
                                    value = String.format("%02d", uiState.editingMinuto ?: 0),
                                    onValueChange = { input ->
                                        input.toIntOrNull()?.let { onMinutoChanged(it) }
                                    },
                                    modifier = Modifier
                                        .width(80.dp)
                                        .defaultMinSize(minHeight = 48.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = Color.White,
                                        unfocusedBorderColor = Color.White,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black
                                    )
                                )
                            }
                        }
                        
                        Button(
                            onClick = onGuardarClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF4C0099)
                            )
                        ) {
                            Text(
                                text = "Guardar Cambios",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4C0099),
                                fontFamily = FontFamily.Default,
                                fontSize = 16.sp
                            )
                        }

                        Button(
                            onClick = onEliminarClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 48.dp),
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
                            text = "Las métricas pasivas de Health Connect no se pueden editar ni eliminar.",
                            color = Color.White,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    
                    TextButton(
                        onClick = onCancelarClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 48.dp)
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
    }
}
