package com.example.demo.dominio.model

import com.example.demo.dominio.model.enums.CategoriaHistorial
import com.example.demo.dominio.model.enums.TipoRegistroHistorial
import java.time.LocalDateTime

data class ItemHistorial(
    val id: Long,
    val nombre: String,
    val detalles: String = "",
    val categoria: CategoriaHistorial,
    val tipoRegistro: TipoRegistroHistorial,
    val fechaHora: LocalDateTime,
    val origenDatos: String,
    val referenciaId: Long
)
