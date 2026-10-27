package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demo.data.local.entidades.FrecuenciaCardiacaEntity

@Dao
interface CardiacaDao {

    //Registrar una pulso cardiaca Health connect
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrarPulsoCardiaca(pulsoCardiaca: FrecuenciaCardiacaEntity): Long

    //Guardar/Actualizar lista de datos de frecuencia cardíaca
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarDatosFrecuenciaCardiaca(datos: List<FrecuenciaCardiacaEntity>)

    //Consultar series temporales
    @Query("SELECT * FROM registros_cardiacos WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun consultarSeriesTemporales(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<FrecuenciaCardiacaEntity>
}