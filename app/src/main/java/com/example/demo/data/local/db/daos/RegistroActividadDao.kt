package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.data.local.entidades.RegistroActividadEntity

@Dao
interface RegistroActividadDao {

    //Registrar actividad fisica de heatlh connect
    @Insert
    suspend fun registrarActividad(actividad: RegistroActividadEntity): Long

    //Consultar actividades por periodo
    @Query("SELECT * FROM registros_actividad WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun consultarActividadesPorPeriodo(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroActividadEntity>

}