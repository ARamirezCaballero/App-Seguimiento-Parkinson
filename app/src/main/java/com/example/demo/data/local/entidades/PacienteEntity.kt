package com.example.demo.data.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "pacientes")
data class PacienteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nombre: String,
    val apellido: String,
    val fechaDiagnostico: String

    ) {
    fun esDatosCompletos(): Boolean {
        return nombre.isNotBlank() &&

                apellido.isNotBlank() &&
                fechaDiagnostico.isNotBlank()
    }

    fun actualizarDatosPersonales(
        nuevoNombre: String,
        nuevoApellido: String,
        nuevaFechaDiagnostico: String
    ): PacienteEntity {
        return this.copy(
            nombre = nuevoNombre.trim(),
            apellido = nuevoApellido.trim(),
            fechaDiagnostico = nuevaFechaDiagnostico.trim()
        )
    }
}