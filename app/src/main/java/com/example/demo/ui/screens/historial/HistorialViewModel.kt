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
        _uiState.update { it.copy(selectedItemForAction = item) }
    }

    fun onDismissActionDialog() {
        _uiState.update { it.copy(selectedItemForAction = null) }
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
