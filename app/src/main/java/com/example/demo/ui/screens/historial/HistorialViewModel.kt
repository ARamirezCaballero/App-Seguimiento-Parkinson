package com.example.demo.ui.screens.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.model.ItemHistorial
import com.example.demo.dominio.model.enums.CategoriaHistorial
import com.example.demo.dominio.usecase.GestionarHistorialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HistorialViewModel @Inject constructor(
    private val gestionarHistorialUseCase: GestionarHistorialUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistorialUiState())
    val uiState: StateFlow<HistorialUiState> = _uiState.asStateFlow()

    init {
        cargarHistorial()
    }

    fun cargarHistorial() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, mensajeError = null) }
            try {
                val currentState = _uiState.value
                val pacienteId = 1L
                val items = gestionarHistorialUseCase.consultarHistorial(
                    pacienteId = pacienteId,
                    fechaInicio = currentState.fechaInicio,
                    fechaFin = currentState.fechaFin,
                    categoria = currentState.categoriaSeleccionada
                )

                _uiState.update {
                    it.copy(
                        itemsHistorial = items,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensajeError = "Error al consultar el historial: ${e.message}"
                    )
                }
            }
        }
    }

    fun onCategoriaSelected(categoria: CategoriaHistorial) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
        cargarHistorial()
    }

    fun onPeriodoSelected(inicio: LocalDate, fin: LocalDate) {
        _uiState.update { it.copy(fechaInicio = inicio, fechaFin = fin) }
        cargarHistorial()
    }

    fun onItemClick(item: ItemHistorial) {
        val severidadParsed = if (item.nombre.contains("Severidad: ")) {
            item.nombre.substringAfter("Severidad: ").substringBefore(")").toIntOrNull() ?: 1
        } else 1
        
        _uiState.update { 
            it.copy(
                selectedItemForAction = item,
                editingSeveridad = severidadParsed,
                editingHora = item.fechaHora.hour,
                editingMinuto = item.fechaHora.minute
            ) 
        }
    }

    fun onDismissActionDialog() {
        _uiState.update { 
            it.copy(
                selectedItemForAction = null,
                isEditing = false
            ) 
        }
    }

    fun onSeveridadChanged(severidad: Int) {
        _uiState.update { it.copy(editingSeveridad = severidad) }
    }

    fun onHoraChanged(hora: Int) {
        _uiState.update { it.copy(editingHora = hora.coerceIn(0, 23)) }
    }

    fun onMinutoChanged(minuto: Int) {
        _uiState.update { it.copy(editingMinuto = minuto.coerceIn(0, 59)) }
    }

    fun guardarCambiosEdicion() {
        val currentState = _uiState.value
        val item = currentState.selectedItemForAction ?: return
        if (currentState.isEditing) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isEditing = true, mensajeError = null, mensajeExito = null) }
            try {
                when (item.tipoRegistro) {
                    com.example.demo.dominio.model.enums.TipoRegistroHistorial.SINTOMA -> {
                        currentState.editingSeveridad?.let { sev ->
                            gestionarHistorialUseCase.actualizarSeveridadSintoma(item.referenciaId, sev)
                        }
                    }
                    com.example.demo.dominio.model.enums.TipoRegistroHistorial.TOMA_MEDICACION -> {
                        if (currentState.editingHora != null && currentState.editingMinuto != null) {
                            gestionarHistorialUseCase.actualizarHoraToma(item.referenciaId, currentState.editingHora, currentState.editingMinuto)
                        }
                    }
                    com.example.demo.dominio.model.enums.TipoRegistroHistorial.ESTADO_MOTOR -> {
                        if (currentState.editingHora != null && currentState.editingMinuto != null) {
                            gestionarHistorialUseCase.actualizarHoraEstadoMotor(item.referenciaId, currentState.editingHora, currentState.editingMinuto)
                        }
                    }
                    else -> {}
                }

                _uiState.update {
                    it.copy(
                        isEditing = false,
                        selectedItemForAction = null,
                        mensajeExito = "Registro actualizado correctamente"
                    )
                }
                cargarHistorial()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isEditing = false,
                        mensajeError = "Error al actualizar el registro: ${e.message}"
                    )
                }
            }
        }
    }

    fun eliminarItemSeleccionado() {
        val currentState = _uiState.value
        val item = currentState.selectedItemForAction ?: return
        if (currentState.isDeleting) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isDeleting = true, mensajeError = null, mensajeExito = null) }
            try {
                gestionarHistorialUseCase.eliminarRegistro(item)

                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        selectedItemForAction = null,
                        mensajeExito = "Registro eliminado correctamente"
                    )
                }
                cargarHistorial()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        mensajeError = "Error al eliminar el registro: ${e.message}"
                    )
                }
            }
        }
    }
}
