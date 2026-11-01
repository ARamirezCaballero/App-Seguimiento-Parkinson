package com.example.demo.ui.screens.configuracionmedicacion.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.ui.components.PrimaryButton
import com.example.demo.ui.components.SecondaryButton
import com.example.demo.ui.screens.configuracionmedicacion.ConfiguracionMedicacionUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Titulo "Agregar Medicación" en Poppins color #4C0099 en bold centrado justo debajo del header
// los textos de indicación del campo van arriba del campo en poppins color negro normal e indicando * para obligatorio
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionMedicacionContent(
    uiState: ConfiguracionMedicacionUiState,
    onNombreMedicacionChanged: (String) -> Unit,
    onPrincipioActivoChanged: (String) -> Unit,
    onDosisGeneralChanged: (String) -> Unit,
    onDosisUnitariaChanged: (String) -> Unit,
    onFormaFarmaceuticaChanged: (String) -> Unit,
    onFechaInicioChanged: (String) -> Unit,
    onFechaFinChanged: (String) -> Unit,
    onUnidadAdministracionChanged: (String) -> Unit,
    onIntervaloAdministracionChanged: (String) -> Unit,
    onHoraInicioChanged: (String) -> Unit,
    onShowFechaInicioPickerChanged: (Boolean) -> Unit,
    onShowFechaFinPickerChanged: (Boolean) -> Unit,
    onShowHoraInicioPickerChanged: (Boolean) -> Unit,
    onGuardarClick: () -> Unit,
    onCancelarClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Titulo "Agregar Medicación" centrado justo debajo del header
        Text(
            text = "Agregar Medicación",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            color = Color(0xFF4C0099),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // campo de texto para ingresar nombre de la medicacion siendo este obligatorio con (*)
        Column {
            FormFieldLabel(text = "Nombre de la medicación", isRequired = true)
            OutlinedTextField(
                value = uiState.nombreMedicacion,
                onValueChange = onNombreMedicacionChanged,
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorNombreMedicacion != null,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
            uiState.errorNombreMedicacion?.let { error ->
                Text(text = error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }

        // Campo de texto para principio activo siendo este opcional
        Column {
            FormFieldLabel(text = "Principio activo", isRequired = false)
            OutlinedTextField(
                value = uiState.principioActivo,
                onValueChange = onPrincipioActivoChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
        }

        // campo de texto para dosis general siendo esta opcional
        Column {
            FormFieldLabel(text = "Dosis general", isRequired = false)
            OutlinedTextField(
                value = uiState.dosisGeneral,
                onValueChange = onDosisGeneralChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
        }

        // campo de texto para dosis unitaria (opcional)
        Column {
            FormFieldLabel(text = "Dosis unitaria", isRequired = false)
            OutlinedTextField(
                value = uiState.dosisUnitaria,
                onValueChange = onDosisUnitariaChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
        }

        // Campo de texto para forma farmaceutica opcional
        Column {
            FormFieldLabel(text = "Forma farmacéutica", isRequired = false)
            OutlinedTextField(
                value = uiState.formaFarmaceutica,
                onValueChange = onFormaFarmaceuticaChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
        }

        // campo de fecha para fecha inicio opcional
        Column {
            FormFieldLabel(text = "Fecha inicio", isRequired = false)
            OutlinedTextField(
                value = uiState.fechaInicio,
                onValueChange = { },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShowFechaInicioPickerChanged(true) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
            uiState.errorFechaInicio?.let { error ->
                Text(text = error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }

        // campo de fecha para fecha fin siendo esta opcional
        Column {
            FormFieldLabel(text = "Fecha fin", isRequired = false)
            OutlinedTextField(
                value = uiState.fechaFin,
                onValueChange = { },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShowFechaFinPickerChanged(true) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
            uiState.errorFechaFin?.let { error ->
                Text(text = error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }

        // Campo para unidad de administración (opcional)
        Column {
            FormFieldLabel(text = "Forma de administración", isRequired = false)
            OutlinedTextField(
                value = uiState.unidadAdministracion,
                onValueChange = onUnidadAdministracionChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
        }

        // Campo numerico para intervalo de administración * con "hs" al lado del campo
        Column {
            FormFieldLabel(text = "Intervalo de administración", isRequired = true)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = uiState.intervaloAdministracion,
                    onValueChange = onIntervaloAdministracionChanged,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.errorIntervaloAdministracion != null,
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = textFieldColors()
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "hs",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            uiState.errorIntervaloAdministracion?.let { error ->
                Text(text = error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }

        // Campo de hora para hora de inicio * con separacion entre hora y minutos
        Column {
            FormFieldLabel(text = "Hora de inicio", isRequired = true)
            OutlinedTextField(
                value = uiState.horaInicio,
                onValueChange = { },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShowHoraInicioPickerChanged(true) },
                isError = uiState.errorHoraInicio != null,
                placeholder = { Text("HH : mm") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors()
            )
            uiState.errorHoraInicio?.let { error ->
                Text(text = error, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // boton de guardar con etiqueta "Guardar"
        PrimaryButton(
            text = "Guardar",
            onClick = onGuardarClick,
            enabled = !uiState.isSaving
        )

        // Boton de cancelar con etiqueta "Cancelar"
        SecondaryButton(
            text = "Cancelar",
            onClick = onCancelarClick,
            enabled = !uiState.isSaving
        )
    }

    // Diálogos para selección de Fecha y Hora
    if (uiState.showFechaInicioPicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { onShowFechaInicioPickerChanged(false) },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val dateStr = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE)
                        onFechaInicioChanged(dateStr)
                    }
                    onShowFechaInicioPickerChanged(false)
                }) { Text("ACEPTAR") }
            },
            dismissButton = {
                TextButton(onClick = { onShowFechaInicioPickerChanged(false) }) { Text("CANCELAR") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (uiState.showFechaFinPicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { onShowFechaFinPickerChanged(false) },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val dateStr = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE)
                        onFechaFinChanged(dateStr)
                    }
                    onShowFechaFinPickerChanged(false)
                }) { Text("ACEPTAR") }
            },
            dismissButton = {
                TextButton(onClick = { onShowFechaFinPickerChanged(false) }) { Text("CANCELAR") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (uiState.showHoraInicioPicker) {
        val timePickerState = rememberTimePickerState(is24Hour = true)
        DatePickerDialog(
            onDismissRequest = { onShowHoraInicioPickerChanged(false) },
            confirmButton = {
                TextButton(onClick = {
                    val formatted = String.format("%02d : %02d", timePickerState.hour, timePickerState.minute)
                    onHoraInicioChanged(formatted)
                    onShowHoraInicioPickerChanged(false)
                }) { Text("ACEPTAR") }
            },
            dismissButton = {
                TextButton(onClick = { onShowHoraInicioPickerChanged(false) }) { Text("CANCELAR") }
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                TimePicker(state = timePickerState)
            }
        }
    }
}

// Etiqueta de campo en poppins color negro normal e indicando * para obligatorio
@Composable
private fun FormFieldLabel(
    text: String,
    isRequired: Boolean,
) {
    Row(
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Default,
            color = Color.Black
        )
        if (isRequired) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "(*)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
    }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFF7162FB),
    unfocusedBorderColor = Color(0xFFC0B8FF),
    focusedLabelColor = Color(0xFF7162FB),
    disabledBorderColor = Color(0xFFC0B8FF),
    disabledTextColor = Color.Black
)
