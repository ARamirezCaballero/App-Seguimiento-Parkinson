package com.example.demo.dominio.repository

import com.example.demo.dominio.model.RegistroEstadoMotor

interface RegistroEstadoMotorRepository {

    suspend fun registrarEstadoMotor(estadoMotor: RegistroEstadoMotor): Long

    suspend fun obtenerEstadoMotorPorId(id: Long): RegistroEstadoMotor?

    suspend fun consultarIntervalosPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroEstadoMotor>

    suspend fun actualizarEstadoMotor(estadoMotor: RegistroEstadoMotor)

    suspend fun eliminarEstadoMotor(estadoMotor: RegistroEstadoMotor)

    suspend fun eliminarEstadoMotorPorId(id: Long)
}