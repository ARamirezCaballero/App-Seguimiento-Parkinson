package com.example.demo.data.local.entidades
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "registros_sueno")
data class RegistroSuenoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val inicio: Int,
    val fin: Int,
    val duracionMinutos: Double,
    val minutosLigeros: Int,
    val minutosProfundos: Int,
    val minutosREM: Int,
    val minutosDespierto: Int,
    val fechaHora: Long,
    val fuente: String
) {
    fun sincronizarSesionSueno(){
        //logica para sincronizar la sesion de sueño
    }

    fun calcularEficienciaSueno(){
        //logica para calcular la eficiencia del sueño
    }
}