package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.RegistroSuenoEntity
import com.example.demo.dominio.model.RegistroSueno
import java.time.Instant
import java.time.ZoneId

fun RegistroSuenoEntity.toDomain() = RegistroSueno(
    id = id,
    pacienteId = pacienteId,
    inicio = inicio,
    fin = fin,
    duracionMinutos = duracionMinutos,
    minutosLigeros = minutosLigeros,
    minutosProfundos = minutosProfundos,
    minutosREM = minutosREM,
    minutosDespierto = minutosDespierto,
    fechaHora = Instant.ofEpochMilli(fechaHora).atZone(ZoneId.systemDefault()).toLocalDateTime(),
    fuente = fuente
)

fun RegistroSueno.toEntity() = RegistroSuenoEntity(
    id = id,
    pacienteId = pacienteId,
    inicio = inicio,
    fin = fin,
    duracionMinutos = duracionMinutos,
    minutosLigeros = minutosLigeros,
    minutosProfundos = minutosProfundos,
    minutosREM = minutosREM,
    minutosDespierto = minutosDespierto,
    fechaHora = fechaHora.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    fuente = fuente
)