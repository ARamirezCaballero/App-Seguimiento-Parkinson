package com.example.demo.dominio.repository

import com.example.demo.dominio.model.RegistroSintoma

interface SintomaRepository {

    suspend fun registrarSintoma(sintoma: RegistroSintoma): Long

    suspend fun obtenerSintomaPorId(id: Long): RegistroSintoma?

    suspend fun consultarSintomasPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroSintoma>

    suspend fun actualizarSintoma(sintoma: RegistroSintoma)

    suspend fun eliminarSintoma(sintoma: RegistroSintoma)

    suspend fun eliminarSintomaPorId(id: Long)
}