package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.data.local.entidades.PautaMedicacionEntity

@Dao
interface PautaMedicacionDao {

    @Insert
    suspend fun insertarPautaMedicacion(pautaMedicacion: PautaMedicacionEntity): Long

    //Consultar pautas vigentes
    @Query("SELECT * FROM pautas_medicaciones WHERE pacienteId = :pacienteId AND fechaFin >= :fechaActual")
    suspend fun consultarPautasVigentes(pacienteId: Long, fechaActual: String): List<PautaMedicacionEntity>

    //Consultar pautas historicas
    @Query("SELECT * FROM pautas_medicaciones WHERE pacienteId = :pacienteId")
    suspend fun consultarPautasHistoricas(pacienteId: Long): List<PautaMedicacionEntity>

    //Finalizar una pauta
    @Query("UPDATE pautas_medicaciones SET activa = 0 WHERE id = :pautaId")
    suspend fun finalizarPauta(pautaId: Long)
}