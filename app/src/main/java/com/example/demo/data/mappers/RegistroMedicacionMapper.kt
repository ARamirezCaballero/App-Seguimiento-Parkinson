package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.RegistroMedicacionEntity
import com.example.demo.data.local.enums.EstadoMedicacion as EstadoMedicacionData
import com.example.demo.dominio.model.RegistroMedicacion
import com.example.demo.dominio.model.enums.EstadoMedicacion as EstadoMedicacionDomain
import java.time.LocalDateTime

fun RegistroMedicacionEntity.toDomain() = RegistroMedicacion(
    id = id,
    pacienteId = pacienteId,
    pautaId = pautaId,
    fechaHoraReal = LocalDateTime.parse(fechaHoraReal),
    fechaHoraProgramada = LocalDateTime.parse(fechaHoraProgramada),
    estado = EstadoMedicacionDomain.valueOf(estado.name),
    cantidad = cantidad
)

fun RegistroMedicacion.toEntity() = RegistroMedicacionEntity(
    id = id,
    pacienteId = pacienteId,
    pautaId = pautaId,
    fechaHoraReal = fechaHoraReal.toString(),
    fechaHoraProgramada = fechaHoraProgramada.toString(),
    estado = EstadoMedicacionData.valueOf(estado.name),
    cantidad = cantidad
)