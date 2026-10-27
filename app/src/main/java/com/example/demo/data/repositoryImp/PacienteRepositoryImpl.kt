package com.example.demo.data.repositoryImp

import com.example.demo.data.local.db.daos.PacienteDao
import com.example.demo.data.mappers.toDomain
import com.example.demo.data.mappers.toEntity
import com.example.demo.dominio.model.Paciente
import com.example.demo.dominio.repository.PacienteRepository
import javax.inject.Inject

class PacienteRepositoryImpl @Inject constructor(
    private val pacienteDao: PacienteDao
) : PacienteRepository {

    //Consulta el perfil mediante el DAO
    override suspend fun obtenerPaciente(): Paciente? {
        return pacienteDao.obtenerPaciente()?.toDomain()
    }

    //Convierte el modelo de dominio a entidad y lo inserta mediante DAO
    override suspend fun insertarPaciente(paciente: Paciente) {
        pacienteDao.insertarPaciente(paciente.toEntity())
    }

    //Convierte el modelo de dominio a entidad y lo actualiza mediante DAO
    override suspend fun actualizarPaciente(paciente: Paciente) {
        pacienteDao.actualizarPaciente(paciente.toEntity())
    }
}