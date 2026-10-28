package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.RegistroSintomaEntity
import com.example.demo.data.local.enums.TipoSintoma as TipoSintomaData
import com.example.demo.dominio.model.RegistroSintoma
import com.example.demo.dominio.model.enums.TipoSintoma as TipoSintomaDomain
import java.time.Instant
import java.time.ZoneId

fun RegistroSintomaEntity.toDomain() = RegistroSintoma(
    id = id,
    pacienteId = pacienteId,
    tipoSintoma = TipoSintomaDomain.valueOf(tipoSintoma.name),
    severidad = severidad,
    fechaHora = Instant.ofEpochMilli(fechaHora).atZone(ZoneId.systemDefault()).toLocalDate()
)

fun RegistroSintoma.toEntity() = RegistroSintomaEntity(
    id = id,
    pacienteId = pacienteId,
    tipoSintoma = TipoSintomaData.valueOf(tipoSintoma.name),
    severidad = severidad,
    fechaHora = fechaHora.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
)