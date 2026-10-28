package com.example.demo.data.repositoryImp

import com.example.demo.data.health.HealthConnectDataSource
import com.example.demo.data.local.db.daos.CardiacaDao
import com.example.demo.data.local.db.daos.RegistroActividadDao
import com.example.demo.data.local.db.daos.RegistroSuenoDao
import com.example.demo.data.mappers.toDomain
import com.example.demo.data.mappers.toEntity
import com.example.demo.dominio.model.FrecuenciaCardiaca
import com.example.demo.dominio.model.RegistroActividad
import com.example.demo.dominio.model.RegistroSueno
import com.example.demo.dominio.repository.HealthDataRepository
import javax.inject.Inject

class HealthDataRepositoryImpl @Inject constructor(
    private val frecuenciaCardiacaDao: CardiacaDao,
    private val registroActividadDao: RegistroActividadDao,
    private val registroSuenoDao: RegistroSuenoDao,
    private val healthConnectDataSource: HealthConnectDataSource
) : HealthDataRepository {

    //Sincronizar datos de health connect coordinando persistencia de resultados
    override suspend fun sincronizarDatosHealthConnect(pacienteId: Long, inicio: Long, fin: Long) {
        val resultado = healthConnectDataSource.sincronizarDatos(pacienteId, inicio, fin)
        if (resultado.actividades.isNotEmpty()) {
            registroActividadDao.guardarDatosActividadFisica(resultado.actividades)
        }
        if (resultado.sesionesSueno.isNotEmpty()) {
            registroSuenoDao.guardarDatosSueno(resultado.sesionesSueno)
        }
        if (resultado.frecuenciasCardiacas.isNotEmpty()) {
            frecuenciaCardiacaDao.guardarDatosFrecuenciaCardiaca(resultado.frecuenciasCardiacas)
        }
    }

    //Insertar datos nuevos o actualizar existentes
    override suspend fun guardarDatosActividadFisica(datos: List<RegistroActividad>) {
        registroActividadDao.guardarDatosActividadFisica(datos.map { it.toEntity() })
    }

    override suspend fun guardarDatosSueno(datos: List<RegistroSueno>) {
        registroSuenoDao.guardarDatosSueno(datos.map { it.toEntity() })
    }

    override suspend fun guardarDatosFrecuenciaCardiaca(datos: List<FrecuenciaCardiaca>) {
        frecuenciaCardiacaDao.guardarDatosFrecuenciaCardiaca(datos.map { it.toEntity() })
    }

    //Consultar datos actividad fisica
    override suspend fun obtenerDatosActividadFisica(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroActividad> {
        return registroActividadDao.consultarActividadesPorPeriodo(pacienteId, inicio, fin).map { it.toDomain() }
    }

    //Consultar datos de sueño
    override suspend fun obtenerDatosSueno(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroSueno> {
        return registroSuenoDao.consultarSesionesPorPeriodo(pacienteId, inicio, fin).map { it.toDomain() }
    }

    //Consultar datos frecuencia cardiaca
    override suspend fun obtenerDatosFrecuenciaCardiaca(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<FrecuenciaCardiaca> {
        return frecuenciaCardiacaDao.consultarSeriesTemporales(pacienteId, inicio, fin).map { it.toDomain() }
    }
}