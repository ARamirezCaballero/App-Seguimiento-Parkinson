package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.data.local.entidades.RegistroMedicacionEntity

@Dao
interface RegistroMedicacionDao {

    //Registrar una toma
    @Insert
    suspend fun registrarToma(toma: RegistroMedicacionEntity): Long

    //Consultar tomas por periodo
    @Query("SELECT * FROM registros_medicaciones WHERE pacienteId = :pacienteId AND fechaHoraReal >= :fechaInicio AND fechaHoraReal < :fechaFin ORDER BY fechaHoraReal ASC")
    suspend fun consultarTomasPorPeriodo(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroMedicacionEntity>

    //Consultar tomas asociadas a una pauta
    @Query("SELECT * FROM registros_medicaciones WHERE pautaId = :pautaId")
    suspend fun consultarTomasPorPauta(pautaId: Long): List<RegistroMedicacionEntity>
}