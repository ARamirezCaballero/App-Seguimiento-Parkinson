package com.example.demo.dominio.model

import java.time.LocalDate

data class PautaMedicacion(

    val id: Long = 0,
    val pacienteId: Long,
    val medicacionId: Long,
    val horaProgramada: Int,
    val cantidad: Double,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val activa: Boolean

) {
}