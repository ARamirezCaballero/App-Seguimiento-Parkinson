package com.example.demo.data.local.entidades
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "registros_medicaciones")
data class RegistroMedicacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val pautaId: Long,
    val fechaHoraReal: Long,
    val fechaHoraProgramada: Long,
    val estado: EstadoMedicacion,
    val cantidad: Double
) {
    enum class EstadoMedicacion {
        PENDIENTE,
        TOMADA,
        NO_TOMADA
    }

    fun registrarToma() {
        // Lógica para registrar la toma de la medicina
    }

    fun editarHorarioDosis(){
        // Lógica para editar el horario de la dosis
    }

    fun calcularDesviacionMinutos(pauta: PautaMedicacionEntity): Long {
        // Lógica para calcular la desviación en minutos
        return 0L
    }

    fun esTomaAtrasada(): Boolean {
        // Lógica para determinar si la toma está atrasada
        return false
    }

    fun anular(){
        // Lógica para anular el registro de la toma
    }

}