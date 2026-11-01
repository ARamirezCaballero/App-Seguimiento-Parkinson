package com.example.demo.ui.screens.configuracionmedicacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.model.Medicacion
import com.example.demo.dominio.model.PautaMedicacion
import com.example.demo.dominio.usecase.GestionarMedicacionUseCase
import com.example.demo.dominio.usecase.GestionarPautaMedicacionUseCase
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
class ConfiguracionMedicacionViewModel @Inject constructor(
    private val gestionarMedicacionUseCase: GestionarMedicacionUseCase,
    private val gestionarPautaMedicacionUseCase: GestionarPautaMedicacionUseCase
) : ViewModel() {

    // Mantiene instancia observable del UiState y expone el estado del Screen para observar
    private val _uiState = MutableStateFlow(ConfiguracionMedicacionUiState())
    val uiState: StateFlow<ConfiguracionMedicacionUiState> = _uiState.asStateFlow()

    // Actualiza los valores de los campos y conserva los datos ingresados
    fun onNombreMedicacionChanged(value: String) {
        _uiState.update { it.copy(nombreMedicacion = value, errorNombreMedicacion = null) }
    }

    fun onPrincipioActivoChanged(value: String) {
        _uiState.update { it.copy(principioActivo = value) }
    }

    fun onDosisGeneralChanged(value: String) {
        _uiState.update { it.copy(dosisGeneral = value) }
    }

    fun onDosisUnitariaChanged(value: String) {
        _uiState.update { it.copy(dosisUnitaria = value) }
    }

    fun onFormaFarmaceuticaChanged(value: String) {
        _uiState.update { it.copy(formaFarmaceutica = value) }
    }

    fun onFechaInicioChanged(value: String) {
        _uiState.update { it.copy(fechaInicio = value, errorFechaInicio = null) }
    }

    fun onFechaFinChanged(value: String) {
        _uiState.update { it.copy(fechaFin = value, errorFechaFin = null) }
    }

    fun onUnidadAdministracionChanged(value: String) {
        _uiState.update { it.copy(unidadAdministracion = value) }
    }

    fun onIntervaloAdministracionChanged(value: String) {
        _uiState.update { it.copy(intervaloAdministracion = value, errorIntervaloAdministracion = null) }
    }

    fun onHoraInicioChanged(value: String) {
        _uiState.update { it.copy(horaInicio = value, errorHoraInicio = null) }
    }

    fun onShowFechaInicioPickerChanged(show: Boolean) {
        _uiState.update { it.copy(showFechaInicioPicker = show) }
    }

    fun onShowFechaFinPickerChanged(show: Boolean) {
        _uiState.update { it.copy(showFechaFinPicker = show) }
    }

    fun onShowHoraInicioPickerChanged(show: Boolean) {
        _uiState.update { it.copy(showHoraInicioPicker = show) }
    }

    // Validacion de campos y procedimiento de guardado
    fun guardarMedicamento() {
        val currentState = _uiState.value

        // verificar que no exista un guardado en curso
        if (currentState.isSaving) return

        // Validacion de campos requeridos
        val nombreInvalido = currentState.nombreMedicacion.isBlank()
        val intervaloParsed = currentState.intervaloAdministracion.toIntOrNull()
        val intervaloInvalido = intervaloParsed == null || intervaloParsed <= 0
        val horaInicioInvalida = currentState.horaInicio.isBlank()

        if (nombreInvalido || intervaloInvalido || horaInicioInvalida) {
            _uiState.update {
                it.copy(
                    errorNombreMedicacion = if (nombreInvalido) "El nombre de la medicación es obligatorio" else null,
                    errorIntervaloAdministracion = if (intervaloInvalido) "El intervalo debe ser un número positivo mayor a 0" else null,
                    errorHoraInicio = if (horaInicioInvalida) "La hora de inicio es obligatoria" else null
                )
            }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isSaving = true, mensajeError = null, mensajeExito = null) }
            try {
                val hoy = LocalDate.now()
                val fechaInicioParsed = try {
                    if (currentState.fechaInicio.isNotBlank()) LocalDate.parse(currentState.fechaInicio) else hoy
                } catch (_: Exception) { hoy }

                val fechaFinParsed = try {
                    if (currentState.fechaFin.isNotBlank()) LocalDate.parse(currentState.fechaFin) else hoy.plusYears(1)
                } catch (_: Exception) { hoy.plusYears(1) }

                // Si datos validos, construir Medicacion
                val medicacion = Medicacion(
                    id = 0L,
                    pacienteId = 1L,
                    principioActivo = currentState.principioActivo.ifBlank { currentState.nombreMedicacion },
                    nombreComercial = currentState.nombreMedicacion.trim(),
                    dosis = currentState.dosisGeneral.ifBlank { currentState.dosisUnitaria },
                    unidadDosis = currentState.unidadAdministracion.ifBlank { "mg" },
                    formaFarmaceutica = currentState.formaFarmaceutica.ifBlank { "Comprimido" },
                    fechaInicio = fechaInicioParsed,
                    fechaFin = fechaFinParsed
                )

                // invocar UseCase encargado de registro de medicacion y pauta
                val medicacionId = gestionarMedicacionUseCase.registrarMedicacion(medicacion)

                val horaInicialInt = try {
                    currentState.horaInicio.replace(" ", "").split(":")[0].toInt()
                } catch (_: Exception) { 8 }

                val dosisCant = currentState.dosisUnitaria.toDoubleOrNull() ?: 1.0

                // guardar Pauta Medicacion con el id de la medicacion creada
                val pauta = PautaMedicacion(
                    id = 0L,
                    pacienteId = 1L,
                    medicacionId = medicacionId,
                    horaInicial = horaInicialInt,
                    intervaloHoras = intervaloParsed,
                    cantidad = dosisCant,
                    fechaInicio = fechaInicioParsed,
                    fechaFin = fechaFinParsed,
                    activa = true
                )

                gestionarPautaMedicacionUseCase.crearPauta(pauta)

                // Actualizar el estado despejando los campos del formulario para permitir un nuevo registro
                _uiState.update {
                    it.copy(
                        nombreMedicacion = "",
                        principioActivo = "",
                        dosisGeneral = "",
                        dosisUnitaria = "",
                        formaFarmaceutica = "",
                        fechaInicio = "",
                        fechaFin = "",
                        unidadAdministracion = "",
                        intervaloAdministracion = "",
                        horaInicio = "",
                        errorNombreMedicacion = null,
                        errorFechaInicio = null,
                        errorFechaFin = null,
                        errorIntervaloAdministracion = null,
                        errorHoraInicio = null,
                        isSaving = false,
                        mensajeExito = "Medicación y pauta guardadas correctamente"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        mensajeError = "Error al guardar el medicamento: ${e.message}"
                    )
                }
            }
        }
    }
}
