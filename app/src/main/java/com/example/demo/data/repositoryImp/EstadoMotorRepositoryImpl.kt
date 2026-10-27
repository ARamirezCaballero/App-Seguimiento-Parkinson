package com.example.demo.data.repositoryImp

import com.example.demo.data.local.db.daos.RegistroEstadoMotorDao
import com.example.demo.data.local.entidades.RegistroEstadoMotorEntity
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
import javax.inject.Inject

class EstadoMotorRepositoryImpl @Inject constructor(
    private val registroEstadoMotorDao: RegistroEstadoMotorDao
) : RegistroEstadoMotorRepository {

    //Insertar el registro de un estado motor
    override suspend fun registrarEstadoMotor(estadoMotor: RegistroEstadoMotorEntity): Long {
        return registroEstadoMotorDao.registrarEstadoMotor(estadoMotor)
    }

    //Recuperar un registro por id
    override suspend fun obtenerEstadoMotorPorId(id: Long): RegistroEstadoMotorEntity? {
        return registroEstadoMotorDao.obtenerEstadoMotorPorId(id)
    }

    //Consultar los registros del periodo
    override suspend fun consultarIntervalosPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroEstadoMotorEntity> {
        return registroEstadoMotorDao.consultarIntervalosPorPeriodo(pacienteId, fechaInicio, fechaFin)
    }
}