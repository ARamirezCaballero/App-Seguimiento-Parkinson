package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.demo.data.local.entidades.RegistroSintomaEntity

@Dao
interface RegistroSintomaDao {

    //Registrar un sintoma
    @Insert
    suspend fun registrarSintoma(sintoma: RegistroSintomaEntity): Long

    //Obtener un síntoma por id
    @Query("SELECT * FROM registros_sintomas WHERE id = :id")
    suspend fun obtenerSintomaPorId(id: Long): RegistroSintomaEntity?

    //Consultar historial por fecha
    @Query("SELECT * FROM registros_sintomas WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun consultarHistorialPorFecha(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroSintomaEntity>

    //recuperar registros para estadisticas
    @Query("SELECT * FROM registros_sintomas WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun recuperarRegistrosParaEstadisticas(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroSintomaEntity>

    //Actualizar un síntoma existente
    @Update
    suspend fun actualizarSintoma(sintoma: RegistroSintomaEntity)

    //Eliminar un síntoma por objeto
    @Delete
    suspend fun eliminarSintoma(sintoma: RegistroSintomaEntity)

    //Eliminar un síntoma por id
    @Query("DELETE FROM registros_sintomas WHERE id = :id")
    suspend fun eliminarSintomaPorId(id: Long)
}