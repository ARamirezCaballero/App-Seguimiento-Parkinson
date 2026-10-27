package com.example.demo.dominio.model

import java.time.LocalDateTime

data class RegistroActividad(
    val id: Long = 0,
    val pacienteId: Long,
    val inicio: Long,
    val fin: Long,
    val pasos: Int,
    val distanciaMetros: Double,
    val fechaHora: LocalDateTime,
    val fuente: String
) {
}