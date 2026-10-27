package com.example.demo.data.repositoryImp

import com.example.demo.data.local.db.daos.RegistroSintomaDao
import com.example.demo.data.local.entidades.RegistroSintomaEntity
import com.example.demo.dominio.repository.SintomaRepository
import javax.inject.Inject

class SintomaRepositoryImpl @Inject constructor(
    private val registroSintomaDao: RegistroSintomaDao
) : SintomaRepository {

    //insertar el registro de un sintoma
    override suspend fun registrarSintoma(sintoma: RegistroSintomaEntity): Long {
        return registroSintomaDao.registrarSintoma(sintoma)
    }

    //Recuperar un sintoma concreto
    override suspend fun obtenerSintomaPorId(id: Long): RegistroSintomaEntity? {
        return registroSintomaDao.obtenerSintomaPorId(id)
    }

    //Consultar los sintoma del periodo
    override suspend fun consultarSintomasPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroSintomaEntity> {
        return registroSintomaDao.consultarHistorialPorFecha(pacienteId, fechaInicio, fechaFin)
    }

    //Actualizar un registro existente para corregirlo
    override suspend fun actualizarSintoma(sintoma: RegistroSintomaEntity) {
        registroSintomaDao.actualizarSintoma(sintoma)
    }
}