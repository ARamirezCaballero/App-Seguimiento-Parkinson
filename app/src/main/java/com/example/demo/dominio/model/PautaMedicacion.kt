package com.example.demo.dominio.model

import java.time.LocalDate

data class PautaMedicacion(
    val id: Long = 0,
    val pacienteId: Long,
    val medicacionId: Long,
    val horaInicial: Int,
    val intervaloHoras: Int,
    val cantidad: Double,
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val activa: Boolean
) {
    fun calcularHorariosDelDia(): List<Int> {
        if (intervaloHoras <= 0) return listOf(horaInicial)
        val horarios = mutableListOf<Int>()
        var hora = horaInicial
        while (hora < 24) {
            horarios.add(hora)
            hora += intervaloHoras
        }
        return horarios
    }
}