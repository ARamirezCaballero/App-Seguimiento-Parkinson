package com.example.demo.dominio.model

import com.example.demo.dominio.model.enums.EstadoMedicacion
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs

data class RegistroMedicacion(
    val id: Long = 0,
    val pacienteId: Long,
    val pautaId: Long,
    val fechaHoraReal: LocalDateTime,
    val fechaHoraProgramada: LocalDateTime,
    val estado: EstadoMedicacion,
    val cantidad: Double
) {
    fun calcularDesfaseMinutos(): Long {
        return abs(Duration.between(fechaHoraProgramada, fechaHoraReal).toMinutes())
    }

    fun esAHorario(margenMinutos: Long = 60L): Boolean {
        return estado == EstadoMedicacion.TOMADA && calcularDesfaseMinutos() <= margenMinutos
    }

    fun esDeshorario(margenMinutos: Long = 60L): Boolean {
        return estado == EstadoMedicacion.TOMADA && calcularDesfaseMinutos() > margenMinutos
    }

    fun esOmitida(): Boolean {
        return estado == EstadoMedicacion.NO_TOMADA
    }
}