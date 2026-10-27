package com.example.demo.data.health

import javax.inject.Inject

class HealthConnectDataSource @Inject constructor() {

    suspend fun sincronizarDatos(inicio: Long, fin: Long) {
        // Lógica de sincronización con Health Connect API
    }
}