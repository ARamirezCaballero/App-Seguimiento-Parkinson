package com.example.demo.dominio.model

import java.time.LocalDateTime

data class FrecuenciaCardiaca(
    val id: Long = 0,
    val pacienteId: Long,
    val valor: Int,
    val fechaHora: LocalDateTime,
    val fuente: String
) {
}