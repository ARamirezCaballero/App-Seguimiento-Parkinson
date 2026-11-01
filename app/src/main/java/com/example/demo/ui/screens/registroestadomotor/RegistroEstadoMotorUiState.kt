package com.example.demo.ui.screens.registroestadomotor

import androidx.compose.ui.graphics.Color
import com.example.demo.dominio.model.enums.EstadoMotor

data class RegistroEstadoMotorUiState(
    val isLoading: Boolean = false,
    val selectedEstadoMotor: EstadoMotor? = null,
    val selectedColor: Color = Color(0xFF95DA4B),
    val horaRegistrada: Int = 8,
    val minutoRegistrado: Int = 0,
    val observaciones: String = "",
    val isSaving: Boolean = false,
    val mensajeError: String? = null,
    val mensajeExito: String? = null
)
