package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.demo.data.local.entidades.PautaMedicacionEntity

@Dao
interface PautaMedicacionDao {

    //Insertar una pauta
    @Insert
    suspend fun insertarPautaMedicacion(pautaMedicacion: PautaMedicacionEntity): Long

    //Consultar pautas asociadas a un medicamento
    @Query("SELECT * FROM pautas_medicaciones WHERE medicacionId = :medicacionId")
    suspend fun obtenerPautasPorMedicacion(medicacionId: Long): List<PautaMedicacionEntity>

    //Consultar pautas vigentes
    @Query("SELECT * FROM pautas_medicaciones WHERE pacienteId = :pacienteId AND fechaFin >= :fechaActual")
    suspend fun consultarPautasVigentes(pacienteId: Long, fechaActual: String): List<PautaMedicacionEntity>

    //Consultar pautas historicas
    @Query("SELECT * FROM pautas_medicaciones WHERE pacienteId = :pacienteId")
    suspend fun consultarPautasHistoricas(pacienteId: Long): List<PautaMedicacionEntity>

    //Consultar una pauta por su id
    @Query("SELECT * FROM pautas_medicaciones WHERE id = :id")
    suspend fun obtenerPautaPorId(id: Long): PautaMedicacionEntity?

    //Actualizar una pauta
    @Update
    suspend fun actualizarPauta(pauta: PautaMedicacionEntity)


    //Finalizar una pauta
    @Query("UPDATE pautas_medicaciones SET activa = 0 WHERE id = :pautaId")
    suspend fun finalizarPauta(pautaId: Long)
}