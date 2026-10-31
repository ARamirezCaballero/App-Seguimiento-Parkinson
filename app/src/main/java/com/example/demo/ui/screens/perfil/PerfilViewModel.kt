package com.example.demo.ui.screens.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.model.Paciente
import com.example.demo.dominio.usecase.GestionarPacienteUseCase
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
class PerfilViewModel @Inject constructor(
    private val gestionarPacienteUseCase: GestionarPacienteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        cargarPerfil()
    }

    //Recupera los datps del paciente mediante el caso de uso gestionar paciente
    fun cargarPerfil() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, mensajeError = null) }
            try {
                val paciente = gestionarPacienteUseCase.obtenerPaciente()
                if (paciente != null) {
                    _uiState.update {
                        it.copy(
                            nombre = paciente.nombre,
                            apellido = paciente.apellido,
                            fechaDiagnostico = paciente.fechaDiagnostico.toString(),
                            isEditing = true,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(isEditing = false, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensajeError = "Error al recuperar el perfil: ${e.message}"
                    )
                }
            }
        }
    }

    //actualizar el valor de nombre en el estado
    fun onNombreChanged(nuevoNombre: String) {
        _uiState.update {
            it.copy(
                nombre = nuevoNombre,
                errorNombre = null
            )
        }
    }

    //actualizar el valor de apellido en el estado
    fun onApellidoChanged(nuevoApellido: String) {
        _uiState.update {
            it.copy(
                apellido = nuevoApellido,
                errorApellido = null
            )
        }
    }

    //actualizar el valor de fecha diagnostico en el estado
    fun onFechaDiagnosticoChanged(nuevaFecha: String) {
        _uiState.update {
            it.copy(
                fechaDiagnostico = nuevaFecha,
                errorFechaDiagnostico = null
            )
        }
    }

    //validar campos obligatorios e invocar el use case de guardar perfil
    fun guardarPerfil() {
        val currentState = _uiState.value
        val nombreInvalido = currentState.nombre.isBlank()
        val apellidoInvalido = currentState.apellido.isBlank()
        val fechaInvalida = currentState.fechaDiagnostico.isBlank()

        if (nombreInvalido || apellidoInvalido || fechaInvalida) {
            _uiState.update {
                it.copy(
                    errorNombre = if (nombreInvalido) "El campo nombre es obligatorio" else null,
                    errorApellido = if (apellidoInvalido) "El campo apellido es obligatorio" else null,
                    errorFechaDiagnostico = if (fechaInvalida) "La fecha de diagnóstico es obligatoria" else null
                )
            }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isSaving = true, mensajeError = null, mensajeExito = null) }
            try {
                val fechaParsed = try {
                    LocalDate.parse(currentState.fechaDiagnostico.trim())
                } catch (_: Exception) {
                    LocalDate.now()
                }

                val paciente = Paciente(
                    id = 1L,
                    nombre = currentState.nombre.trim(),
                    apellido = currentState.apellido.trim(),
                    fechaDiagnostico = fechaParsed
                )

                if (currentState.isEditing) {
                    gestionarPacienteUseCase.actualizarPaciente(paciente)
                } else {
                    gestionarPacienteUseCase.insertarPaciente(paciente)
                }

                //Actualizar estado y comunicar confirmacion
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isEditing = true,
                        mensajeExito = "Perfil guardado correctamente"
                    )
                }
            } catch (e: Exception) {
                //Error de persistencia para mostrar mensaje correspondientes y conservar datos ingresados
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        mensajeError = "Error al guardar el perfil: ${e.message}"
                    )
                }
            }
        }
    }
}
