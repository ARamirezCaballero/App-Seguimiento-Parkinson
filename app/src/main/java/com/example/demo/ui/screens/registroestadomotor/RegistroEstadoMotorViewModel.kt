package com.example.demo.ui.screens.registroestadomotor

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.model.RegistroEstadoMotor
import com.example.demo.dominio.model.enums.EstadoMotor
import com.example.demo.dominio.usecase.GestionarEstadoMotorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime

@HiltViewModel
class RegistroEstadoMotorViewModel @Inject constructor(
    private val gestionarEstadoMotorUseCase: GestionarEstadoMotorUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroEstadoMotorUiState())
    val uiState: StateFlow<RegistroEstadoMotorUiState> = _uiState.asStateFlow()

    fun onEstadoMotorSelected(estadoMotor: EstadoMotor, color: Color) {
        val ahora = LocalTime.now()
        _uiState.update {
            it.copy(
                selectedEstadoMotor = estadoMotor,
                selectedColor = color,
                horaRegistrada = ahora.hour,
                minutoRegistrado = ahora.minute
            )
        }
    }

    fun onHoraChanged(hora: Int) {
        _uiState.update { it.copy(horaRegistrada = hora.coerceIn(0, 23)) }
    }

    fun onMinutoChanged(minuto: Int) {
        _uiState.update { it.copy(minutoRegistrado = minuto.coerceIn(0, 59)) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(selectedEstadoMotor = null) }
    }

    fun registrarEstadoMotor() {
        val currentState = _uiState.value
        val estado = currentState.selectedEstadoMotor ?: return
        if (currentState.isSaving) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isSaving = true, mensajeError = null, mensajeExito = null) }
            try {
                val pacienteId = 1L
                val registro = RegistroEstadoMotor(
                    id = 0L,
                    pacienteId = pacienteId,
                    estadoMotor = estado,
                    fechaHora = LocalDateTime.now()
                        .withHour(currentState.horaRegistrada)
                        .withMinute(currentState.minutoRegistrado),
                    observacion = currentState.observaciones
                )

                gestionarEstadoMotorUseCase.registrarEstadoMotor(registro)

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        selectedEstadoMotor = null,
                        mensajeExito = "Estado Motor ${estado.name} registrado correctamente"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        mensajeError = "Error al registrar estado motor: ${e.message}"
                    )
                }
            }
        }
    }
}
