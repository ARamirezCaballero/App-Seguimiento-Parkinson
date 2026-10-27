package com.example.demo.data.mappers

import com.example.demo.data.local.entidades.PacienteEntity
import com.example.demo.dominio.model.Paciente
import java.time.LocalDate

fun PacienteEntity.toDomain() = Paciente(
    id = id,
    nombre = nombre,
    apellido = apellido,
    fechaDiagnostico = LocalDate.parse(fechaDiagnostico)
)

fun Paciente.toEntity() = PacienteEntity(
    id = id,
    nombre = nombre,
    apellido = apellido,
    fechaDiagnostico = fechaDiagnostico.toString()
)