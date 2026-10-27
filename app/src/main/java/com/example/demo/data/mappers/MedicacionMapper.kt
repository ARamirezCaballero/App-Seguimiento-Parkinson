package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.MedicacionEntity
import com.example.demo.dominio.model.Medicacion
import java.time.LocalDate

fun MedicacionEntity.toDomain() = Medicacion(
    id = id,
    pacienteId = pacienteId,
    principioActivo = principioActivo,
    nombreComercial = nombreComercial,
    dosis = dosis,
    unidadDosis = unidadDosis,
    formaFarmaceutica = formaFarmaceutica,
    fechaInicio = LocalDate.parse(fechaInicio),
    fechaFin = LocalDate.parse(fechaFin)
)

fun Medicacion.toEntity() = MedicacionEntity(
    id = id,
    pacienteId = pacienteId,
    principioActivo = principioActivo,
    nombreComercial = nombreComercial,
    dosis = dosis,
    unidadDosis = unidadDosis,
    formaFarmaceutica = formaFarmaceutica,
    fechaInicio = fechaInicio.toString(),
    fechaFin = fechaFin.toString()
)