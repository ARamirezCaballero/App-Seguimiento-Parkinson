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
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, mensajeError = null) }
            try {
                val pacienteId = 1L
                val medicamentos = gestionarMedicacionUseCase.consultarMedicamentos(pacienteId)
                val items = medicamentos.map { med ->
                    val pautas = gestionarPautaMedicacionUseCase.consultarPautas(med.id)
                    MedicamentoConPautaItem(
                        medicacion = med,
                        pauta = pautas.firstOrNull { it.activa } ?: pautas.firstOrNull()
                    )
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
        viewModelScope.launch(Dispatchers.IO) {
            val pautaId = item.pauta?.id ?: 0L
            val ultimaToma = gestionarTomaMedicamentoUseCase.obtenerUltimaTomaPorPauta(pautaId)
            
            val horaDefecto: Int
            val minutoDefecto: Int
            
            if (ultimaToma != null && item.pauta != null) {
                // Si hay una toma previa, la siguiente sugerida es: hora programada previa + intervalo
                val siguienteHoraSugerida = ultimaToma.fechaHoraProgramada.plusHours(item.pauta.intervaloHoras.toLong())
                horaDefecto = siguienteHoraSugerida.hour
                minutoDefecto = siguienteHoraSugerida.minute
            } else {
                // Si es la primera vez, se sugiere la hora inicial de la pauta
                horaDefecto = item.pauta?.horaInicial ?: LocalTime.now().hour
                minutoDefecto = 0
            }

            _uiState.update {
                it.copy(
                    selectedMedicamento = item,
                    horaRegistrada = horaDefecto,
                    minutoRegistrado = minutoDefecto
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
        _uiState.update { it.copy(selectedMedicamento = null) }
    }

    fun confirmarToma() {
        val currentState = _uiState.value
        val selectedItem = currentState.selectedMedicamento ?: return
        if (currentState.isSaving) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isSaving = true, mensajeError = null, mensajeExito = null) }
            try {
                val pacienteId = 1L
                val ahora = LocalDateTime.now()
                val fechaHoraReal = ahora
                    .withHour(currentState.horaRegistrada)
                    .withMinute(currentState.minutoRegistrado)

                val horaProg = selectedItem.pauta?.horaInicial ?: currentState.horaRegistrada
                val fechaHoraProgramada = ahora.withHour(horaProg).withMinute(0)

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
