package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.RegistroEstadoMotorEntity
import com.example.demo.dominio.model.RegistroEstadoMotor
import com.example.demo.dominio.model.enums.EstadoMotor
import java.time.Instant
import java.time.ZoneId

fun RegistroEstadoMotorEntity.toDomain() = RegistroEstadoMotor(
    id = id,
    pacienteId = pacienteId,
    estadoMotor = EstadoMotor.valueOf(estadoMotor.name),
    fechaHora = Instant.ofEpochMilli(fechaHora).atZone(ZoneId.systemDefault()).toLocalDateTime(),
    observacion = observacion,
    horaCarga = Instant.ofEpochMilli(horaCarga).atZone(ZoneId.systemDefault()).toLocalDateTime()
)

fun RegistroEstadoMotor.toEntity() = RegistroEstadoMotorEntity(
    id = id,
    pacienteId = pacienteId,
    estadoMotor = com.example.demo.data.local.enums.EstadoMotor.valueOf(estadoMotor.name),
    fechaHora = fechaHora.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    observacion = observacion,
    horaCarga = horaCarga.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
)