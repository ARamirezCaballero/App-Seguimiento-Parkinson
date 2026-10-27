package com.example.demo.dominio.repository

import com.example.demo.data.local.entidades.RegistroSintomaEntity

interface SintomaRepository {

    suspend fun registrarSintoma(sintoma: RegistroSintomaEntity): Long

    suspend fun obtenerSintomaPorId(id: Long): RegistroSintomaEntity?

    suspend fun consultarSintomasPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroSintomaEntity>

    suspend fun actualizarSintoma(sintoma: RegistroSintomaEntity)
}