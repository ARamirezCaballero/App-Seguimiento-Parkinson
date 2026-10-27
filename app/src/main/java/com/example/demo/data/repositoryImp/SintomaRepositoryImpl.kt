package com.example.demo.data.repositoryImp

import com.example.demo.data.local.db.daos.RegistroSintomaDao
import com.example.demo.data.mappers.toDomain
import com.example.demo.data.mappers.toEntity
import com.example.demo.dominio.model.RegistroSintoma
import com.example.demo.dominio.repository.SintomaRepository
import javax.inject.Inject

class SintomaRepositoryImpl @Inject constructor(
    private val registroSintomaDao: RegistroSintomaDao
) : SintomaRepository {

    //insertar el registro de un sintoma
    override suspend fun registrarSintoma(sintoma: RegistroSintoma): Long {
        return registroSintomaDao.registrarSintoma(sintoma.toEntity())
    }

    //Recuperar un sintoma concreto
    override suspend fun obtenerSintomaPorId(id: Long): RegistroSintoma? {
        return registroSintomaDao.obtenerSintomaPorId(id)?.toDomain()
    }

    //Consultar los sintoma del periodo
    override suspend fun consultarSintomasPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroSintoma> {
        return registroSintomaDao.consultarHistorialPorFecha(pacienteId, fechaInicio, fechaFin).map { it.toDomain() }
    }

    //Actualizar un registro existente para corregirlo
    override suspend fun actualizarSintoma(sintoma: RegistroSintoma) {
        registroSintomaDao.actualizarSintoma(sintoma.toEntity())
    }

    //Eliminar un registro de síntoma por objeto
    override suspend fun eliminarSintoma(sintoma: RegistroSintoma) {
        registroSintomaDao.eliminarSintoma(sintoma.toEntity())
    }

    //Eliminar un registro de síntoma por id
    override suspend fun eliminarSintomaPorId(id: Long) {
        registroSintomaDao.eliminarSintomaPorId(id)
    }
}