package com.example.demo.data.repositoryImp

import com.example.demo.data.local.db.daos.RegistroEstadoMotorDao
import com.example.demo.data.mappers.toDomain
import com.example.demo.data.mappers.toEntity
import com.example.demo.dominio.model.RegistroEstadoMotor
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
import javax.inject.Inject

class EstadoMotorRepositoryImpl @Inject constructor(
    private val registroEstadoMotorDao: RegistroEstadoMotorDao
) : RegistroEstadoMotorRepository {

    //Insertar el registro de un estado motor
    override suspend fun registrarEstadoMotor(estadoMotor: RegistroEstadoMotor): Long {
        return registroEstadoMotorDao.registrarEstadoMotor(estadoMotor.toEntity())
    }

    //Recuperar un registro por id
    override suspend fun obtenerEstadoMotorPorId(id: Long): RegistroEstadoMotor? {
        return registroEstadoMotorDao.obtenerEstadoMotorPorId(id)?.toDomain()
    }

    //Consultar los registros del periodo
    override suspend fun consultarIntervalosPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroEstadoMotor> {
        return registroEstadoMotorDao.consultarIntervalosPorPeriodo(pacienteId, fechaInicio, fechaFin).map { it.toDomain() }
    }

    //Actualizar estado motor
    override suspend fun actualizarEstadoMotor(estadoMotor: RegistroEstadoMotor) {
        registroEstadoMotorDao.actualizarEstadoMotor(estadoMotor.toEntity())
    }

    //Eliminar estado motor por objeto
    override suspend fun eliminarEstadoMotor(estadoMotor: RegistroEstadoMotor) {
        registroEstadoMotorDao.eliminarEstadoMotor(estadoMotor.toEntity())
    }

    //Eliminar estado motor por id
    override suspend fun eliminarEstadoMotorPorId(id: Long) {
        registroEstadoMotorDao.eliminarEstadoMotorPorId(id)
    }
}