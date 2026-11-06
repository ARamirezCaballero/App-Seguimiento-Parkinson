package com.example.demo.ui.screens.registrotoma

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.model.RegistroMedicacion
import com.example.demo.dominio.model.enums.EstadoMedicacion
import com.example.demo.dominio.usecase.GestionarMedicacionUseCase
import com.example.demo.dominio.usecase.GestionarPautaMedicacionUseCase
import com.example.demo.dominio.usecase.GestionarTomaMedicamentoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RegistroTomaViewModel @Inject constructor(
    private val gestionarMedicacionUseCase: GestionarMedicacionUseCase,
    private val gestionarPautaMedicacionUseCase: GestionarPautaMedicacionUseCase,
    private val gestionarTomaMedicamentoUseCase: GestionarTomaMedicamentoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroTomaUiState())
    val uiState: StateFlow<RegistroTomaUiState> = _uiState.asStateFlow()

    init {
        cargarMedicamentos()
    }

    fun cargarMedicamentos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensajeError = null) }
            try {
                val pacienteId = 1L
                val medicamentos = gestionarMedicacionUseCase.consultarMedicamentos(pacienteId)
                val items = medicamentos.mapNotNull { med ->
                    val pautas = gestionarPautaMedicacionUseCase.consultarPautas(med.id)
                    val pautaActiva = pautas.firstOrNull { it.activa }
                    if (pautaActiva != null) {
                        MedicamentoConPautaItem(
                            medicacion = med,
                            pauta = pautaActiva
                        )
                    } else {
                        null
                    }
                }

                _uiState.update {
                    it.copy(
                        medicamentosConPauta = items,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensajeError = "Error al cargar medicamentos: ${e.message}"
                    )
                }
            }
        }
    }

    fun onMedicamentoSelected(item: MedicamentoConPautaItem) {
        viewModelScope.launch {
            val pautaId = item.pauta?.id ?: 0L
            val ultimaToma = gestionarTomaMedicamentoUseCase.obtenerUltimaTomaPorPauta(pautaId)
            
            val fechaHoraProgramadaSugerida: LocalDateTime
            
            if (ultimaToma != null && item.pauta != null) {
                // Si hay una toma previa, la siguiente sugerida es: hora programada de ultima toma + intervalo
                fechaHoraProgramadaSugerida = ultimaToma.fechaHoraProgramada.plusHours(item.pauta.intervaloHoras.toLong())
            } else {
                // Si es la primera vez, se sugiere la hora inicial de la pauta
                val ahora = LocalDateTime.now().withSecond(0).withNano(0)
                val horaInicial = item.pauta?.horaInicial ?: LocalTime.now().hour
                fechaHoraProgramadaSugerida = ahora.withHour(horaInicial).withMinute(0)
            }

            _uiState.update {
                it.copy(
                    selectedMedicamento = item,
                    fechaHoraProgramadaSugerida = fechaHoraProgramadaSugerida,
                    horaRegistrada = fechaHoraProgramadaSugerida.hour,
                    minutoRegistrado = fechaHoraProgramadaSugerida.minute
                )
            }
        }
    }

    fun onHoraRegistradaChanged(hora: Int) {
        _uiState.update { it.copy(horaRegistrada = hora.coerceIn(0, 23)) }
    }

    fun onMinutoRegistradoChanged(minuto: Int) {
        _uiState.update { it.copy(minutoRegistrado = minuto.coerceIn(0, 59)) }
    }

    fun onDismissDialog() {
        _uiState.update {
            it.copy(
                selectedMedicamento = null,
                fechaHoraProgramadaSugerida = null,
                showFirstFinalizarConfirmation = false,
                showSecondFinalizarConfirmation = false
            )
        }
    }

    fun onIniciarFinalizarTratamiento() {
        _uiState.update { it.copy(showFirstFinalizarConfirmation = true) }
    }

    fun onConfirmarPrimeraEtapaFinalizar() {
        _uiState.update {
            it.copy(
                showFirstFinalizarConfirmation = false,
                showSecondFinalizarConfirmation = true
            )
        }
    }

    fun onCancelarFinalizarTratamiento() {
        _uiState.update {
            it.copy(
                showFirstFinalizarConfirmation = false,
                showSecondFinalizarConfirmation = false
            )
        }
    }

    fun onConfirmarSegundaEtapaFinalizar() {
        val currentState = _uiState.value
        val selectedItem = currentState.selectedMedicamento ?: return
        val pautaId = selectedItem.pauta?.id ?: return
        if (currentState.isSaving) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isSaving = true, mensajeError = null, mensajeExito = null) }
            try {
                gestionarPautaMedicacionUseCase.desactivarPauta(pautaId)

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        selectedMedicamento = null,
                        fechaHoraProgramadaSugerida = null,
                        showFirstFinalizarConfirmation = false,
                        showSecondFinalizarConfirmation = false,
                        mensajeExito = "Tratamiento de ${selectedItem.medicacion.nombreComercial} finalizado correctamente"
                    )
                }
                cargarMedicamentos()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        mensajeError = "Error al finalizar el tratamiento: ${e.message}"
                    )
                }
            }
        }
    }

    fun confirmarToma() {
        val currentState = _uiState.value
        val selectedItem = currentState.selectedMedicamento ?: return
        if (currentState.isSaving) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, mensajeError = null, mensajeExito = null) }
            try {
                val pacienteId = 1L
                val ahora = LocalDateTime.now()

                val fechaHoraProgramada = currentState.fechaHoraProgramadaSugerida
                    ?: ahora.withHour(currentState.horaRegistrada).withMinute(0)

                val fechaBase = fechaHoraProgramada.toLocalDate()
                val fechaHoraReal = fechaBase
                    .atTime(currentState.horaRegistrada, currentState.minutoRegistrado)

                val pautaId = selectedItem.pauta?.id ?: 0L
                val cant = selectedItem.pauta?.cantidad ?: 1.0

                val toma = RegistroMedicacion(
                    id = 0L,
                    pacienteId = pacienteId,
                    pautaId = pautaId,
                    fechaHoraReal = fechaHoraReal,
                    fechaHoraProgramada = fechaHoraProgramada,
                    estado = EstadoMedicacion.TOMADA,
                    cantidad = cant
                )

                gestionarTomaMedicamentoUseCase.registrarToma(toma)

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        selectedMedicamento = null,
                        fechaHoraProgramadaSugerida = null,
                        mensajeExito = "Toma registrada correctamente"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        mensajeError = "Error al registrar la toma: ${e.message}"
                    )
                }
            }
        }
    }
}
