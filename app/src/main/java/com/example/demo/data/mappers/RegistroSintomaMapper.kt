package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.RegistroSintomaEntity
import com.example.demo.dominio.model.RegistroSintoma
import java.time.Instant
import java.time.ZoneId

fun RegistroSintomaEntity.toDomain() = RegistroSintoma(
    id = id,
    pacienteId = pacienteId,
    tipoSintoma = tipoSintoma,
    severidad = severidad,
    fechaHora = Instant.ofEpochMilli(fechaHora).atZone(ZoneId.systemDefault()).toLocalDate()
)

fun RegistroSintoma.toEntity() = RegistroSintomaEntity(
    id = id,
    pacienteId = pacienteId,
    tipoSintoma = tipoSintoma,
    severidad = severidad,
    fechaHora = fechaHora.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
)