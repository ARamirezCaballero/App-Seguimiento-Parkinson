package com.example.demo.data.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "pautas_medicaciones")
data class PautaMedicacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val medicacionId: Long,
    val horaProgramada: Int,
    val cantidad: Double,
    val activa: Boolean
) {
    fun calcularProximaToma(): String {
        // Lógica para calcular la próxima toma de la medicina
        return ""
    }

    fun esMomentoDeToma(): Boolean {
        // Lógica para determinar si es momento de tomar la medicina
        return false
    }
    fun actualizarPauta(){
        // Lógica para actualizar la pauta de la pauta
    }
    
    fun eliminarPauta(){
        // Lógica para eliminar la pauta de la pauta
    }
}