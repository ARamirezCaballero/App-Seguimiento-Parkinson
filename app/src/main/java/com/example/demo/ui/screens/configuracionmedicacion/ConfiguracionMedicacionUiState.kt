package com.example.demo.ui.screens.configuracionmedicacion

data class ConfiguracionMedicacionUiState(
    // Campos del formulario de medicación
    val nombreMedicacion: String = "",
    val principioActivo: String = "",
    val dosisGeneral: String = "",
    val dosisUnitaria: String = "",
    val formaFarmaceutica: String = "",
    val fechaInicio: String = "",
    val fechaFin: String = "",
    val unidadAdministracion: String = "",
    val intervaloAdministracion: String = "",
    val horaInicio: String = "",

    // Validacion de errores en los campos
    val errorNombreMedicacion: String? = null,
    val errorFechaInicio: String? = null,
    val errorFechaFin: String? = null,
    val errorIntervaloAdministracion: String? = null,
    val errorHoraInicio: String? = null,

    // Estados de proceso y mensajes
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val mensajeError: String? = null,
    val mensajeExito: String? = null,

    // Selector de fecha y hora
    val showFechaInicioPicker: Boolean = false,
    val showFechaFinPicker: Boolean = false,
    val showHoraInicioPicker: Boolean = false
)
