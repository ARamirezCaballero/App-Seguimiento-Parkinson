package com.example.demo.ui.screens.home

data class HomeUiState(
    //fecha y hora de la ultima sincronizacion con Helath Connect
    val lastSyncDate: String? = null,
    //Indicar si la app tiene permisos necesarios con Health Connect
    val hasHealthPermissions: Boolean = false,
    //Indicar si se está sincronizando con Health Connect
    val isSyncing: Boolean = false,
    //Mensaje de error que no se puede recuperar estado de sincronización
    val syncErrorMessage: String? = null,
    val isLoading: Boolean = false,
)
