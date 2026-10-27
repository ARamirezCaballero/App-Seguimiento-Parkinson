package com.example.demo.dominio.model

import java.time.LocalDateTime

data class RegistroSueno(
    val id: Long = 0,
    val pacienteId: Long,
    val inicio: Int,
    val fin: Int,
    val duracionMinutos: Double,
    val minutosLigeros: Int,
    val minutosProfundos: Int,
    val minutosREM: Int,
    val minutosDespierto: Int,
    val fechaHora: LocalDateTime,
    val fuente: String
) {
}