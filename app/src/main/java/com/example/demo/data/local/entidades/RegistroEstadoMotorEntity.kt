package com.example.demo.data.local.entidades
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.demo.data.local.enums.EstadoMotor
@Entity(tableName ="registros_estado_motor")
data class RegistroEstadoMotorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val estadoMotor: EstadoMotor,
    val fechaHora: Long,
    val observacion: String
) {
    fun registrarEstadoMotor() {
        // Lógica para registrar el estado del motor
    }
    fun esEstadoON(){
        // Lógica para determinar si el estado es ON
    }

    fun esEstadoOFF(){
        // Lógica para determinar si el estado es OFF
    }

    fun esEstadoDiscinesia(){
        // Lógica para determinar si el estado es discinesia
    }

    fun esEstadoBloqueado(){
        // Lógica para determinar si el estado es bloqueado
    }
}