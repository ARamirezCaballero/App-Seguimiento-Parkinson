package com.example.demo.dominio.model

import com.example.demo.dominio.model.enums.EstadoMedicacion
import java.time.LocalDate

data class RegistroMedicacion(
    val id: Long,
    val pacienteId: Long,
    val pautaId: Long,
    val fechaHoraReal: LocalDate,
    val fechaHoraProgramada: LocalDate,
    val estado: EstadoMedicacion,
    val cantidad: Double
) {
}