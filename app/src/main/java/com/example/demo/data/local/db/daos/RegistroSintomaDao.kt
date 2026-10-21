package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.data.local.entidades.RegistroSintomaEntity

@Dao
interface RegistroSintomaDao {

    //Registrar un sintoma
    @Insert
    suspend fun registrarSintoma(sintoma: RegistroSintomaEntity): Long

    //Consultar historial por fecha
    @Query("SELECT * FROM registros_sintomas WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun consultarHistorialPorFecha(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroSintomaEntity>

    //recuperar registros para estadisticas
    @Query("SELECT * FROM registros_sintomas WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun recuperarRegistrosParaEstadisticas(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroSintomaEntity>

}