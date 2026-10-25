package com.example.demo.data.repositoryImp

import com.example.demo.data.local.entidades.RegistroEstadoMotorEntity

interface EstadoMotorRepositoryImp {

    //Guardar un registro de estado motor

    suspend fun guardarEstadoMotor(estadoMotor: RegistroEstadoMotorEntity)

    //Recuperar un registro especifico

    suspend fun obtenerEstadoMotor(id: Long): RegistroEstadoMotorEntity?

    //Consultar los registros de un periodo

    suspend fun obtenerEstadosMotorPorIntervalo(inicio: Long, fin: Long): List<RegistroEstadoMotorEntity>
}