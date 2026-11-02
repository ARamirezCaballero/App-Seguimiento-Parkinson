package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//etiqueta "Fecha de diagnostico" con indicador de obligatorio (*)
//Campo que muestra fecha seleccionada y con selector de fecha de Material Design 3
//Mensaje de validacion por campo obligatorio vacio
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FechaDiagnosticoField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        //Campo que muestra fecha seleccionada y con selector de fecha de Material Design 3
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            label = {
                //etiqueta "Fecha de diagnostico" con indicador de obligatorio (*)
                Text(text = "Fecha de diagnóstico *")
            },
            placeholder = { Text(text = "AAAA-MM-DD") },
            isError = errorMessage != null,
            supportingText = {
                //Mensaje de validacion por campo obligatorio vacio
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true
        )

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                val formatted = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                                onValueChange(formatted)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
