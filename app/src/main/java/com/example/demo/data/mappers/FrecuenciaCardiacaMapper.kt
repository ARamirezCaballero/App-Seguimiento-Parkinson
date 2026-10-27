package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.FrecuenciaCardiacaEntity
import com.example.demo.dominio.model.FrecuenciaCardiaca
import java.time.Instant
import java.time.ZoneId

fun FrecuenciaCardiacaEntity.toDomain() = FrecuenciaCardiaca(
    id = id,
    pacienteId = pacienteId,
    valor = valor,
    fechaHora = Instant.ofEpochMilli(fechaHora).atZone(ZoneId.systemDefault()).toLocalDateTime(),
    fuente = fuente
)

fun FrecuenciaCardiaca.toEntity() = FrecuenciaCardiacaEntity(
    id = id,
    pacienteId = pacienteId,
    valor = valor,
    fechaHora = fechaHora.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    fuente = fuente
)