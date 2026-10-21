package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.data.local.entidades.RegistroSuenoEntity

@Dao
interface RegistroSuenoDao {

    //Sincronización de sesiones de sueño con Health Connect
    @Insert
    suspend fun registrarSesionSueno(sesionSueno: RegistroSuenoEntity): Long
    // Consultar sesiones de sueño por periodo
    @Query("SELECT * FROM registros_sueno WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun consultarSesionesPorPeriodo(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroSuenoEntity>
}