package com.example.demo.dominio.repository

import com.example.demo.data.local.entidades.FrecuenciaCardiacaEntity
import com.example.demo.data.local.entidades.RegistroActividadEntity
import com.example.demo.data.local.entidades.RegistroSuenoEntity

interface HealthDataRepository {

    suspend fun sincronizarDatosHealthConnect(inicio: Long, fin: Long)

    suspend fun guardarDatosActividadFisica(datos: List<RegistroActividadEntity>)

    suspend fun guardarDatosSueno(datos: List<RegistroSuenoEntity>)

    suspend fun guardarDatosFrecuenciaCardiaca(datos: List<FrecuenciaCardiacaEntity>)

    suspend fun obtenerDatosActividadFisica(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroActividadEntity>

    suspend fun obtenerDatosSueno(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroSuenoEntity>

    suspend fun obtenerDatosFrecuenciaCardiaca(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<FrecuenciaCardiacaEntity>
}