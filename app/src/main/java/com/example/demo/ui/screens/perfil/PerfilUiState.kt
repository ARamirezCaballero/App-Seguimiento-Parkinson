package com.example.demo.ui.screens.perfil

data class PerfilUiState(
    //Nombre del paciente (tiene que ser obligatorio)
    val nombre: String = "",
    //Apellido del paciente, obligatorio
    val apellido: String = "",
    //Fecha de diagnostivo (obligatorio)
    val fechaDiagnostico: String = "",
    //logica para error de validacion (campo nombre no completado)
    val errorNombre: String? = null,
    //logica para error de validacion (campo apellido no completado)
    val errorApellido: String? = null,
    //logica para error de validacion (campo fecha no completado)
    val errorFechaDiagnostico: String? = null,
    //isLoading
    val isLoading: Boolean = false,
    //isSaving
    val isSaving: Boolean = false,
    //Mensajde de error producido durante operacion
    val mensajeError: String? = null,
    //Mensaje de confirmacion de guardado
    val mensajeExito: String? = null,
    //isEditing para indicar modificacion del perfil existente
    val isEditing: Boolean = false,
)
