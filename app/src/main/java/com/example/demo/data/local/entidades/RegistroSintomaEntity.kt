package com.example.demo.data.local.entidades
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.demo.data.local.enums.TipoSintoma
@Entity(tableName = "registros_sintomas")
data class RegistroSintomaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val tipoSintoma: TipoSintoma,
    val severidad: Int,
    val fechaHora: Long
) {
    fun registrarSintoma() {
        // Lógica para registrar el sintoma
    }

    fun esSintomaGrave(): Boolean {
        // Lógica para determinar si el sintoma es grave
        return false
    }

}