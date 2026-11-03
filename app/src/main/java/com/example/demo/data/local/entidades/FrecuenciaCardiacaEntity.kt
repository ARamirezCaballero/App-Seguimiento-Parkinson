package com.example.demo.data.local.entidades
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "registros_cardiacos")
data class FrecuenciaCardiacaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val valor: Int,
    val fechaHora: Long,
    val fuente: String
)