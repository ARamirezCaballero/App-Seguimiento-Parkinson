package com.example.demo.data.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "medicaciones")
data class MedicacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val pacienteId: Long,
    val principioActivo: String,
    val nombreComercial: String,
    val dosis: String,
    val unidadDosis: String,
    val formaFarmaceutica: String,
    val fechaInicio: String,
    val fechaFin: String,
){
    fun nombreCompleto(): String {
        return "$principioActivo - $nombreComercial"
    }
}