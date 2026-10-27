package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.PautaMedicacionEntity
import com.example.demo.dominio.model.PautaMedicacion
import java.time.LocalDate

fun PautaMedicacionEntity.toDomain() = PautaMedicacion(
    id = id,
    pacienteId = pacienteId,
    medicacionId = medicacionId,
    horaProgramada = horaProgramada,
    cantidad = cantidad,
    fechaInicio = LocalDate.parse(fechaInicio),
    fechaFin = LocalDate.parse(fechaFin),
    activa = activa
)

fun PautaMedicacion.toEntity() = PautaMedicacionEntity(
    id = id,
    pacienteId = pacienteId,
    medicacionId = medicacionId,
    horaProgramada = horaProgramada,
    cantidad = cantidad,
    fechaInicio = fechaInicio.toString(),
    fechaFin = fechaFin.toString(),
    activa = activa
)