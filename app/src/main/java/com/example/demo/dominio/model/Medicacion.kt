package com.example.demo.dominio.model

import java.time.LocalDate

data class Medicacion (
    val id: Long,
    val pacienteId: Long,
    val principioActivo: String,
    val nombreComercial: String,
    val dosis: String,
    val unidadDosis: String,
    val formaFarmaceutica: String,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    ) {
}