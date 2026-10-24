package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.demo.data.local.entidades.PacienteEntity

@Dao
interface PacienteDao {
    @Insert
    suspend fun insertarPaciente(paciente: PacienteEntity): Long

    @Query("SELECT * FROM pacientes WHERE id = :id")
    suspend fun obtenerPacientePorId(id: Long): PacienteEntity?

    @Query("SELECT * FROM pacientes LIMIT 1")
    suspend fun obtenerPaciente(): PacienteEntity?

    @Update
    suspend fun actualizarPaciente(paciente: PacienteEntity)

    @Delete
    suspend fun eliminarPaciente(paciente: PacienteEntity)
}