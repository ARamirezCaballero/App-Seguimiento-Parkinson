package com.example.demo.ui.screens.registrosintomas

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.model.RegistroSintoma
import com.example.demo.dominio.model.enums.TipoSintoma
import com.example.demo.dominio.usecase.GestionarSintomaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RegistroSintomasViewModel @Inject constructor(
    private val gestionarSintomaUseCase: GestionarSintomaUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialEsMotor: Boolean = savedStateHandle.get<Boolean>("esMotor") ?: true

    private val _uiState = MutableStateFlow(RegistroSintomasUiState(esMotor = initialEsMotor))
    val uiState: StateFlow<RegistroSintomasUiState> = _uiState.asStateFlow()

    fun setTipoMotor(esMotor: Boolean) {
        _uiState.update { it.copy(esMotor = esMotor) }
    }

    fun onSintomaSelected(tipoSintoma: TipoSintoma, color: Color) {
        _uiState.update {
            it.copy(
                selectedSintoma = tipoSintoma,
                selectedColor = color
            )
        }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(selectedSintoma = null) }
    }

    fun registrarSintoma(severidad: Int) {
        val currentState = _uiState.value
        val tipo = currentState.selectedSintoma ?: return
        if (currentState.isSaving) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isSaving = true, mensajeError = null, mensajeExito = null) }
            try {
                val pacienteId = 1L
                val registro = RegistroSintoma(
                    id = 0L,
                    pacienteId = pacienteId,
                    tipoSintoma = tipo,
                    severidad = severidad,
                    fechaHora = LocalDateTime.now()
                )

                gestionarSintomaUseCase.registrarSintoma(registro)

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        selectedSintoma = null,
                        mensajeExito = "Síntoma ${tipo.titulo} registrado correctamente"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        mensajeError = "Error al registrar síntoma: ${e.message}"
                    )
                }
            }
        }
    }
}
