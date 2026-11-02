package com.example.demo.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.repository.HealthDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    //Usar HealthDataRepository
    private val healthDataRepository: HealthDataRepository,
) : ViewModel() {

    //StateFlow para representar la pantalla mientras se recupera info de Health Connect
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        //Recuperar info inicial de la pantalla
        cargarEstadoInicial()
    }

    //Actualizar estado cunado se abre la pantalla ya sea de vuelta o por primera vez
    fun onResume() {
        cargarEstadoInicial()
        sincronizarHealthConnect()
    }

    //Operaciones
    //Recuperar info inicial de la pantalla
    fun cargarEstadoInicial() {
        viewModelScope.launch(Dispatchers.IO) {
            //GEstionar los estados de carga y error
            _uiState.update { it.copy(isLoading = true, syncErrorMessage = null) }
            try {
                //Consultar estado actual de autoizacion con Health Connect
                val tienePermisos = true

                //Recuperar fecha y hora de la última Consulta existosa
                val fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))

                //Actualizar HomeUiState con los resultados
                _uiState.update {
                    it.copy(
                        lastSyncDate = fechaActual,
                        hasHealthPermissions = tienePermisos,
                        isLoading = false,
                        isSyncing = false
                    )
                }
                
                sincronizarHealthConnect()
            } catch (e: Exception) {
                //GEstionar los estados de carga y error
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSyncing = false,
                        syncErrorMessage = "No se pudo recuperar el estado de sincronización: ${e.message}"
                    )
                }
            }
        }
    }

    //Actualizar estado de sincronizacion con Health Connect y permisos
    fun sincronizarHealthConnect(pacienteId: Long = 1L) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isSyncing = true, syncErrorMessage = null) }
            try {
                val fin = System.currentTimeMillis()
                val inicio = fin - (24 * 60 * 60 * 1000L)
                healthDataRepository.sincronizarDatosHealthConnect(pacienteId, inicio, fin)

                val fechaSincro = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                _uiState.update {
                    it.copy(
                        lastSyncDate = fechaSincro,
                        isSyncing = false,
                        hasHealthPermissions = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSyncing = false,
                        syncErrorMessage = "Error al sincronizar con Health Connect: ${e.message}"
                    )
                }
            }
        }
    }
}
