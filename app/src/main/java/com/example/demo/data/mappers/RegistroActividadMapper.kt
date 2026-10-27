package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.RegistroActividadEntity
import com.example.demo.dominio.model.RegistroActividad
import java.time.Instant
import java.time.ZoneId

fun RegistroActividadEntity.toDomain() = RegistroActividad(
    id = id,
    pacienteId = pacienteId,
    inicio = inicio,
    fin = fin,
    pasos = pasos,
    distanciaMetros = distanciaMetros,
    fechaHora = Instant.ofEpochMilli(fechaHora).atZone(ZoneId.systemDefault()).toLocalDateTime(),
    fuente = fuente
)

fun RegistroActividad.toEntity() = RegistroActividadEntity(
    id = id,
    pacienteId = pacienteId,
    inicio = inicio,
    fin = fin,
    pasos = pasos,
    distanciaMetros = distanciaMetros,
    fechaHora = fechaHora.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    fuente = fuente
)