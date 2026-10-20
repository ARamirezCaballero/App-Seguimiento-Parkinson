package com.example.demo.data.local.entidades
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "registros_sintomas")
data class RegistroSintomaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val tipoSintoma: TipoSintoma,
    val severidad: Int,
    val fechaHora: Long
) {
    enum class TipoSintoma{
        //enums
    }

    fun registrarSintoma() {
        // Lógica para registrar el sintoma
    }

    fun esSintomaGrave(): Boolean {
        // Lógica para determinar si el sintoma es grave
        return false
    }

}