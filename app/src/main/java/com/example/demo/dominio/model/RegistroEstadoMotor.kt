package com.example.demo.dominio.model

import java.time.LocalDateTime
import com.example.demo.dominio.model.enums.EstadoMotor

data class RegistroEstadoMotor(
    val id: Long = 0,
    val pacienteId: Long,
    val estadoMotor: EstadoMotor,
    val fechaHora: LocalDateTime,
    val observacion: String
) {
}