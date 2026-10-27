package com.example.demo.dominio.repository

import com.example.demo.data.local.entidades.RegistroEstadoMotorEntity

interface RegistroEstadoMotorRepository {

    suspend fun registrarEstadoMotor(estadoMotor: RegistroEstadoMotorEntity): Long

    suspend fun obtenerEstadoMotorPorId(id: Long): RegistroEstadoMotorEntity?

    suspend fun consultarIntervalosPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroEstadoMotorEntity>
}