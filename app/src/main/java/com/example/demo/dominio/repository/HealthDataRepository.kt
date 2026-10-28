package com.example.demo.dominio.repository

import com.example.demo.dominio.model.FrecuenciaCardiaca
import com.example.demo.dominio.model.RegistroActividad
import com.example.demo.dominio.model.RegistroSueno

interface HealthDataRepository {

    suspend fun sincronizarDatosHealthConnect(pacienteId: Long, inicio: Long, fin: Long)

    suspend fun guardarDatosActividadFisica(datos: List<RegistroActividad>)

    suspend fun guardarDatosSueno(datos: List<RegistroSueno>)

    suspend fun guardarDatosFrecuenciaCardiaca(datos: List<FrecuenciaCardiaca>)

    suspend fun obtenerDatosActividadFisica(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroActividad>

    suspend fun obtenerDatosSueno(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroSueno>

    suspend fun obtenerDatosFrecuenciaCardiaca(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<FrecuenciaCardiaca>
}