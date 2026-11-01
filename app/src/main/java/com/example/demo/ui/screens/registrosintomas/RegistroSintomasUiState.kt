package com.example.demo.ui.screens.registrosintomas

import androidx.compose.ui.graphics.Color
import com.example.demo.dominio.model.enums.TipoSintoma

data class RegistroSintomasUiState(
    val esMotor: Boolean = true,
    val selectedSintoma: TipoSintoma? = null,
    val selectedColor: Color = Color(0xFFFC836B),
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val mensajeError: String? = null,
    val mensajeExito: String? = null
)
