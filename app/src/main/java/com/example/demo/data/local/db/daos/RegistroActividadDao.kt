package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demo.data.local.entidades.RegistroActividadEntity

@Dao
interface RegistroActividadDao {

    //Registrar actividad fisica de heatlh connect
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrarActividad(actividad: RegistroActividadEntity): Long

    //Guardar/Actualizar lista de actividades físicas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarDatosActividadFisica(datos: List<RegistroActividadEntity>)

    //Consultar actividades por periodo
    @Query("SELECT * FROM registros_actividad WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun consultarActividadesPorPeriodo(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroActividadEntity>
}