package com.example.demo.data.repositoryImp

import com.example.demo.data.local.db.daos.MedicacionDao
import com.example.demo.data.local.db.daos.PautaMedicacionDao
import com.example.demo.data.mappers.toDomain
import com.example.demo.data.mappers.toEntity
import com.example.demo.dominio.model.Medicacion
import com.example.demo.dominio.model.PautaMedicacion
import com.example.demo.dominio.repository.MedicacionRepository
import javax.inject.Inject

class MedicacionRepositoryImpl @Inject constructor(
    private val medicacionDao: MedicacionDao,
    private val pautaMedicacionDao: PautaMedicacionDao
) : MedicacionRepository {

    //Insertar medicamento mediante DAO
    override suspend fun insertarMedicacion(medicacion: Medicacion): Long {
        return medicacionDao.insertarMedicacion(medicacion.toEntity())
    }

    //Consultar un medicamento por id
    override suspend fun obtenerMedicacionPorId(id: Long): Medicacion? {
        return medicacionDao.obtenerMedicacionPorId(id)?.toDomain()
    }

    //Recuperar medicamentos registrados de un paciente
    override suspend fun consultarMedicamentos(pacienteId: Long): List<Medicacion> {
        return medicacionDao.consultarMedicamentos(pacienteId).map { it.toDomain() }
    }

    //Consultar medicamentos activos de un paciente
    override suspend fun consultarMedicamentosActivos(
        pacienteId: Long,
        fechaActual: String
    ): List<Medicacion> {
        return medicacionDao.consultarMedicamentosActivos(pacienteId, fechaActual).map { it.toDomain() }
    }

    //Actualizar datos del medicamento
    override suspend fun actualizarMedicacion(medicacion: Medicacion) {
        medicacionDao.actualizarMedicacion(medicacion.toEntity())
    }

    //Insertar pauta mediante DAO
    override suspend fun insertarPauta(pauta: PautaMedicacion): Long {
        return pautaMedicacionDao.insertarPautaMedicacion(pauta.toEntity())
    }

    //Consultar una pauta específica por su id
    override suspend fun obtenerPautaPorId(id: Long): PautaMedicacion? {
        return pautaMedicacionDao.obtenerPautaPorId(id)?.toDomain()
    }

    //Consultar pautas asociadas a un medicamento
    override suspend fun consultarPautas(medicacionId: Long): List<PautaMedicacion> {
        return pautaMedicacionDao.obtenerPautasPorMedicacion(medicacionId).map { it.toDomain() }
    }

    //Consultar pautas vigentes de un paciente
    override suspend fun consultarPautasVigentes(
        pacienteId: Long,
        fechaActual: String
    ): List<PautaMedicacion> {
        return pautaMedicacionDao.consultarPautasVigentes(pacienteId, fechaActual).map { it.toDomain() }
    }

    //Consultar pautas históricas de un paciente
    override suspend fun consultarPautasHistoricas(pacienteId: Long): List<PautaMedicacion> {
        return pautaMedicacionDao.consultarPautasHistoricas(pacienteId).map { it.toDomain() }
    }

    //Actualizar pauta
    override suspend fun actualizarPauta(pauta: PautaMedicacion) {
        pautaMedicacionDao.actualizarPauta(pauta.toEntity())
    }

    //Finalizar pauta
    override suspend fun finalizarPauta(pautaId: Long) {
        pautaMedicacionDao.finalizarPauta(pautaId)
    }
}