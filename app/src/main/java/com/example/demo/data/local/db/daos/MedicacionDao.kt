package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.demo.data.local.entidades.MedicacionEntity

@Dao
interface MedicacionDao {

    //Insertar un medicamento
    @Insert
    suspend fun insertarMedicacion(medicacion: MedicacionEntity): Long

    //Consultar los medicamentos del paciente
    @Query("SELECT * FROM medicaciones WHERE pacienteId = :pacienteId")
    suspend fun consultarMedicamentos(pacienteId: Long): List<MedicacionEntity>

    //Consultar los medicamentos activos del paciente
    @Query("SELECT * FROM medicaciones WHERE pacienteId = :pacienteId AND fechaFin >= :fechaActual")
    suspend fun consultarMedicamentosActivos(pacienteId: Long, fechaActual: String): List<MedicacionEntity>

    //Actualizar información de un medicamento
    @Update
    suspend fun actualizarMedicacion(medicacion: MedicacionEntity)
}