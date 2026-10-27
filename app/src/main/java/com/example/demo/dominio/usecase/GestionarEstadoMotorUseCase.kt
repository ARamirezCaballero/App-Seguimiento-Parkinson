package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.RegistroEstadoMotor
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
import javax.inject.Inject

class GestionarEstadoMotorUseCase @Inject constructor(
    private val registroEstadoMotorRepository: RegistroEstadoMotorRepository
) {

    //Registrar el estado motor
    suspend fun registrarEstadoMotor(estadoMotor: RegistroEstadoMotor): Long {
        return registroEstadoMotorRepository.registrarEstadoMotor(estadoMotor)
    }

    //obtener estado motor por id
    suspend fun obtenerEstadoMotorPorId(id: Long): RegistroEstadoMotor? {
        return registroEstadoMotorRepository.obtenerEstadoMotorPorId(id)
    }

    //obtener estados motores por periodo
    suspend fun consultarIntervalosPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroEstadoMotor> {
        return registroEstadoMotorRepository.consultarIntervalosPorPeriodo(pacienteId, fechaInicio, fechaFin)
    }

    //Actualizar estado motor para corregir registro
    suspend fun actualizarEstadoMotor(estadoMotor: RegistroEstadoMotor) {
        registroEstadoMotorRepository.actualizarEstadoMotor(estadoMotor)
    }

    //Eliminar estado motor
    suspend fun eliminarEstadoMotor(estadoMotor: RegistroEstadoMotor) {
        registroEstadoMotorRepository.eliminarEstadoMotor(estadoMotor)
    }

    suspend fun eliminarEstadoMotorPorId(id: Long) {
        registroEstadoMotorRepository.eliminarEstadoMotorPorId(id)
    }
}