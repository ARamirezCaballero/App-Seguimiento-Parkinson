package com.example.demo.data.local.entidades

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "registros_actividad",
    indices = [Index(value = ["healthRecordId"], unique = true)]
)
data class RegistroActividadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val inicio: Long,
    val fin: Long,
    val pasos: Int,
    val distanciaMetros: Double,
    val fechaHora: Long,
    val fuente: String,
    val healthRecordId: String? = null
)