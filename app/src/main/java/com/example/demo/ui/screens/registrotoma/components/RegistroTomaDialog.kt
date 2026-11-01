package com.example.demo.ui.screens.registrotoma.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import com.example.demo.dominio.model.Medicacion
import com.example.demo.dominio.model.PautaMedicacion
import com.example.demo.ui.screens.registrotoma.MedicamentoConPautaItem
import com.example.demo.ui.theme.DemoTheme
import java.time.LocalDate

// Titulo centrado "nombre medicamento" " dosis general" Poppins bold blanco
// dialog color 7EA6E0 ocupando el centro de la pantalla, el fondo es la pantalla de content y header pero oscurecida un 25%
// contenido de dialog: "Hora de la toma:"
// Campo de hora uno para la hora separado por : y otra para los minutos con la hora predeterminada proporcionada por el calculo de la pauta
// Campo de hora tiene la opcion de poder cambiar la hora de registro
// Boton de "Confirmar" arriba y "Cancelar" abajo, ambos blancos con texto Poppins negro bold (al menos 14mm / 56dp de alto)
@Composable
fun RegistroTomaDialog(
    item: MedicamentoConPautaItem,
    hora: Int,
    minuto: Int,
    onHoraChanged: (Int) -> Unit,
    onMinutoChanged: (Int) -> Unit,
    onConfirmarClick: () -> Unit,
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
        RegistroTomaDialogContent(
            item = item,
            hora = hora,
            minuto = minuto,
            onHoraChanged = onHoraChanged,
            onMinutoChanged = onMinutoChanged,
            onConfirmarClick = onConfirmarClick,
            onCancelarClick = onCancelarClick,
            modifier = modifier
        )
    }
}

@Composable
fun RegistroTomaDialogContent(
    item: MedicamentoConPautaItem,
    hora: Int,
    minuto: Int,
    onHoraChanged: (Int) -> Unit,
    onMinutoChanged: (Int) -> Unit,
    onConfirmarClick: () -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF7EA6E0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Titulo centrado "nombre medicamento" " dosis general" Poppins bold blanco
            Text(
                text = item.displayNombreYDosis,
                color = Color.White,
                fontSize = 18.sp,
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

            // Campos de hora (hora : minuto) con altura minima de 56dp (14mm)
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

            // Botones verticales: Confirmar (arriba) y Cancelar (abajo) con al menos 56dp (14mm) de alto
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onConfirmarClick,
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
                        text = "Confirmar",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Default
                    )
                }

                Button(
                    onClick = onCancelarClick,
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
                        text = "Cancelar",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = FontFamily.Default
                    )
                }
            }
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
fun RegistroTomaDialogPreview() {
    DemoTheme {
        val mockItem = MedicamentoConPautaItem(
            medicacion = Medicacion(
                id = 1L,
                pacienteId = 1L,
                principioActivo = "Levodopa",
                nombreComercial = "Levodopa / Carbidopa",
                dosis = "100 mg / 25 mg",
                unidadDosis = "Comprimidos",
                formaFarmaceutica = "Comprimido",
                fechaInicio = LocalDate.now(),
                fechaFin = LocalDate.now().plusYears(1)
            ),
            pauta = PautaMedicacion(
                id = 1L,
                pacienteId = 1L,
                medicacionId = 1L,
                horaInicial = 8,
                intervaloHoras = 8,
                cantidad = 1.0,
                fechaInicio = LocalDate.now(),
                fechaFin = LocalDate.now().plusYears(1),
                activa = true
            )
        )

        RegistroTomaDialogContent(
            item = mockItem,
            hora = 8,
            minuto = 30,
            onHoraChanged = {},
            onMinutoChanged = {},
            onConfirmarClick = {},
            onCancelarClick = {}
        )
    }
}
