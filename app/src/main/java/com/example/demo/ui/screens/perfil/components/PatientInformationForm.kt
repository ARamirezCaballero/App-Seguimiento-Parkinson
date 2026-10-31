package com.example.demo.ui.screens.perfil.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PatientInformationForm(
    nombre: String,
    onNombreChange: (String) -> Unit,
    errorNombre: String?,
    apellido: String,
    onApellidoChange: (String) -> Unit,
    errorApellido: String?,
    fechaDiagnostico: String,
    onFechaDiagnosticoChange: (String) -> Unit,
    errorFechaDiagnostico: String?,
    modifier: Modifier = Modifier,
) {
    // Estructura para el formulario de información del paciente (sin lógica)
    //se compone de NombrePacienteField, ApellidoPacienteField y FechaDiagnosticoField
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NombrePacienteField(
            value = nombre,
            onValueChange = onNombreChange,
            errorMessage = errorNombre
        )
        ApellidoPacienteField(
            value = apellido,
            onValueChange = onApellidoChange,
            errorMessage = errorApellido
        )
        FechaDiagnosticoField(
            value = fechaDiagnostico,
            onValueChange = onFechaDiagnosticoChange,
            errorMessage = errorFechaDiagnostico
        )
    }
}
