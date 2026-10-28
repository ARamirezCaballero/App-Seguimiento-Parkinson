package com.example.demo.dominio.model

import com.example.demo.dominio.model.enums.TipoSintoma
import java.time.LocalDate

data class RegistroSintoma(
    val id: Long = 0,
    val pacienteId: Long,
    val tipoSintoma: TipoSintoma,
    val severidad: Int,
    val fechaHora: LocalDate
)