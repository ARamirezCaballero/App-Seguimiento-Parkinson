package com.example.demo.ui.screens.registroestadomotor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.demo.dominio.model.enums.EstadoMotor
import com.example.demo.ui.theme.DemoTheme

// Dialog con titulo del Estado Motor elegido y color de la tarjeta del estado motor elegido
// campo de hora donde uno es HH y otro mm separados por un ":"
// La hora se pone predeterminadamente con el horario de eleccion del estado
// Debe permitir cambiar de horario si es que se desea
// Boton de guardar o cancelar
@Composable
fun EstadoMotorDialog(
    estadoMotor: EstadoMotor,
    containerColor: Color,
    hora: Int,
    minuto: Int,
    onHoraChanged: (Int) -> Unit,
    onMinutoChanged: (Int) -> Unit,
    onGuardarClick: () -> Unit,
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
            EstadoMotorDialogContent(
                estadoMotor = estadoMotor,
                containerColor = containerColor,
                hora = hora,
                minuto = minuto,
                onHoraChanged = onHoraChanged,
                onMinutoChanged = onMinutoChanged,
                onGuardarClick = onGuardarClick,
                onCancelarClick = onCancelarClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun EstadoMotorDialogContent(
    estadoMotor: EstadoMotor,
    containerColor: Color,
    hora: Int,
    minuto: Int,
    onHoraChanged: (Int) -> Unit,
    onMinutoChanged: (Int) -> Unit,
    onGuardarClick: () -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = containerColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Titulo del Estado Motor elegido
                Text(
                    text = estadoMotor.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Hora de la toma:",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                // Campos de hora (HH : mm) con altura minima de 56dp (14mm)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = String.format("%02d", hora),
                        onValueChange = { input ->
                            input.toIntOrNull()?.let { onHoraChanged(it) }
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .defaultMinSize(minHeight = 56.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        colors = dialogTextFieldColors()
                    )

                    Text(
                        text = ":",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    OutlinedTextField(
                        value = String.format("%02d", minuto),
                        onValueChange = { input ->
                            input.toIntOrNull()?.let { onMinutoChanged(it) }
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .defaultMinSize(minHeight = 56.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        colors = dialogTextFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Boton de Guardar dentro de la tarjeta
                Button(
                    onClick = onGuardarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Guardar",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Default,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Boton de Cancelar afuera en texto blanco
        TextButton(
            onClick = onCancelarClick,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp)
        ) {
            Text(
                text = "Cancelar",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun dialogTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color.White,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black
)

@Preview(showBackground = true)
@Composable
fun EstadoMotorDialogPreview() {
    DemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            EstadoMotorDialogContent(
                estadoMotor = EstadoMotor.ON,
                containerColor = Color(0xFF95DA4B),
                hora = 8,
                minuto = 30,
                onHoraChanged = {},
                onMinutoChanged = {},
                onGuardarClick = {},
                onCancelarClick = {}
            )
        }
    }
}
