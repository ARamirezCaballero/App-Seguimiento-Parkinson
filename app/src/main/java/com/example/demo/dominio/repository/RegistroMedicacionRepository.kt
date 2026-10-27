package com.example.demo.dominio.repository

import com.example.demo.dominio.model.RegistroMedicacion

interface RegistroMedicacionRepository {

    suspend fun registrarToma(toma: RegistroMedicacion): Long

    suspend fun obtenerTomaPorId(id: Long): RegistroMedicacion?

    suspend fun consultarTomasPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroMedicacion>

    suspend fun consultarTomasPorPauta(pautaId: Long): List<RegistroMedicacion>

    suspend fun actualizarToma(toma: RegistroMedicacion)
}