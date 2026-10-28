package com.example.demo.data.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pautas_medicaciones")
data class PautaMedicacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val medicacionId: Long,
    val horaInicial: Int,
    val intervaloHoras: Int,
    val cantidad: Double,
    val fechaInicio: String,
    val fechaFin: String,
    val activa: Boolean
)