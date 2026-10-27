package com.example.demo.data.local.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.demo.data.local.entidades.RegistroEstadoMotorEntity

@Dao
interface RegistroEstadoMotorDao {
    //Registrar un estado motor
    @Insert
    suspend fun registrarEstadoMotor(estadoMotor: RegistroEstadoMotorEntity): Long

    //Obtener un registro por id
    @Query("SELECT * FROM registros_estado_motor WHERE id = :id")
    suspend fun obtenerEstadoMotorPorId(id: Long): RegistroEstadoMotorEntity?

    //Consultar intervalos por periodo
    @Query("SELECT * FROM registros_estado_motor WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun consultarIntervalosPorPeriodo(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroEstadoMotorEntity>

    //Recuperar registros para calcular estadisticas
    @Query("SELECT * FROM registros_estado_motor WHERE pacienteId = :pacienteId AND fechaHora >= :fechaInicio AND fechaHora < :fechaFin ORDER BY fechaHora ASC")
    suspend fun recuperarRegistrosParaEstadisticas(pacienteId: Long, fechaInicio: Long, fechaFin: Long): List<RegistroEstadoMotorEntity>

    //Actualizar un registro de estado motor
    @Update
    suspend fun actualizarEstadoMotor(estadoMotor: RegistroEstadoMotorEntity)

    //Eliminar un registro de estado motor
    @Delete
    suspend fun eliminarEstadoMotor(estadoMotor: RegistroEstadoMotorEntity)

    //Eliminar un registro de estado motor por id
    @Query("DELETE FROM registros_estado_motor WHERE id = :id")
    suspend fun eliminarEstadoMotorPorId(id: Long)
}