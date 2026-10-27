package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demo.data.local.entidades.RegistroSuenoEntity

@Dao
interface RegistroSuenoDao {

    //Sincronización de sesiones de sueño con Health Connect
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrarSesionSueno(sesionSueno: RegistroSuenoEntity): Long

    //Guardar/Actualizar lista de sesiones de sueño
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarDatosSueno(datos: List<RegistroSuenoEntity>)

    // Consultar sesiones de sueño por periodo
    @Query("SELECT * FROM registros_sueno WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun consultarSesionesPorPeriodo(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroSuenoEntity>
}