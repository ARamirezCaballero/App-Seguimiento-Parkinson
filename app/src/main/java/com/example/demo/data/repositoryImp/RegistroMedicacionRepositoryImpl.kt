package com.example.demo.data.repositoryImp

import com.example.demo.data.local.db.daos.RegistroMedicacionDao
import com.example.demo.data.mappers.toDomain
import com.example.demo.data.mappers.toEntity
import com.example.demo.dominio.model.RegistroMedicacion
import com.example.demo.dominio.repository.RegistroMedicacionRepository
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class RegistroMedicacionRepositoryImpl @Inject constructor(
    private val registroMedicacionDao: RegistroMedicacionDao
) : RegistroMedicacionRepository {

    //insertar el registro de una toma
    override suspend fun registrarToma(toma: RegistroMedicacion): Long {
        return registroMedicacionDao.registrarToma(toma.toEntity())
    }

    //Recuperar toma concreta
    override suspend fun obtenerTomaPorId(id: Long): RegistroMedicacion? {
        return registroMedicacionDao.obtenerTomaPorId(id)?.toDomain()
    }

    //Consultar las tomas del períod solicitado
    override suspend fun consultarTomasPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroMedicacion> {
        val inicioStr = Instant.ofEpochMilli(fechaInicio).atZone(ZoneId.systemDefault()).toLocalDateTime().toString()
        val finStr = Instant.ofEpochMilli(fechaFin).atZone(ZoneId.systemDefault()).toLocalDateTime().toString()
        return registroMedicacionDao.consultarTomasPorPeriodo(pacienteId, inicioStr, finStr).map { it.toDomain() }
    }

    //Consultar tomas asociadas a una pauta
    override suspend fun consultarTomasPorPauta(pautaId: Long): List<RegistroMedicacion> {
        return registroMedicacionDao.consultarTomasPorPauta(pautaId).map { it.toDomain() }
    }
    
    //Obtener la ultima toma de una pauta
    override suspend fun obtenerUltimaTomaPorPauta(pautaId: Long): RegistroMedicacion? {
        return registroMedicacionDao.obtenerUltimaTomaPorPauta(pautaId)?.toDomain()
    }

    //Actualizar una toma existente para corregirla
    override suspend fun actualizarToma(toma: RegistroMedicacion) {
        registroMedicacionDao.actualizarToma(toma.toEntity())
    }

    //Eliminar una toma concreta por id
    override suspend fun eliminarTomaPorId(id: Long) {
        registroMedicacionDao.eliminarTomaPorId(id)
    }
}