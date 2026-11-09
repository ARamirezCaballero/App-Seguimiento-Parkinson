package com.example.demo.ui.screens.evolucion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.repository.PacienteRepository
import com.example.demo.dominio.usecase.ProcesarIndicadoresUseCase
import com.example.demo.ui.screens.informe.PeriodoOpcion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class EvolucionViewModel @Inject constructor(
    private val procesarIndicadoresUseCase: ProcesarIndicadoresUseCase,
    private val pacienteRepository: PacienteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EvolucionUiState())
    val uiState: StateFlow<EvolucionUiState> = _uiState.asStateFlow()

    init {
        cargarIndicadores()
    }

    fun seleccionarPeriodo(periodo: PeriodoOpcion) {
        _uiState.update { it.copy(periodoSeleccionado = periodo) }
        cargarIndicadores()
    }

    fun cargarIndicadores() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val periodo = _uiState.value.periodoSeleccionado
                val hoy = LocalDate.now()
                val inicioLocal = hoy.minusDays(periodo.dias.toLong())

                val fechaFinEpoch = hoy.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                val fechaInicioEpoch = inicioLocal.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

                val paciente = pacienteRepository.obtenerPaciente()
                val pacienteId = paciente?.id ?: 1L

                val distMotor = procesarIndicadoresUseCase.calcularDistribucionEstadoMotor(pacienteId, fechaInicioEpoch, fechaFinEpoch)
                val sintMotores = procesarIndicadoresUseCase.obtenerSintomasMotoresPorPeriodo(pacienteId, fechaInicioEpoch, fechaFinEpoch)
                val sintNoMotores = procesarIndicadoresUseCase.obtenerSintomasNoMotoresPorPeriodo(pacienteId, fechaInicioEpoch, fechaFinEpoch)
                val adherencia = procesarIndicadoresUseCase.calcularAdherenciaMedicacion(pacienteId, fechaInicioEpoch, fechaFinEpoch)
                val interrupciones = procesarIndicadoresUseCase.calcularInterrupcionesSueno(pacienteId, fechaInicioEpoch, fechaFinEpoch)
                val suenoList = procesarIndicadoresUseCase.obtenerEvolucionSuenoPorPeriodo(pacienteId, fechaInicioEpoch, fechaFinEpoch)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        distribucionEstadoMotor = distMotor,
                        sintomasMotores = sintMotores,
                        sintomasNoMotores = sintNoMotores,
                        adherenciaMedicacion = adherencia,
                        interrupcionSueno = interrupciones,
                        registrosSueno = suenoList
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
