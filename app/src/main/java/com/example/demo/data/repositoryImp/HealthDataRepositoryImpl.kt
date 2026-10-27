package com.example.demo.data.repositoryImp

import com.example.demo.data.health.HealthConnectDataSource
import com.example.demo.data.local.db.daos.CardiacaDao
import com.example.demo.data.local.db.daos.RegistroActividadDao
import com.example.demo.data.local.db.daos.RegistroSuenoDao
import com.example.demo.data.local.entidades.FrecuenciaCardiacaEntity
import com.example.demo.data.local.entidades.RegistroActividadEntity
import com.example.demo.data.local.entidades.RegistroSuenoEntity
import com.example.demo.dominio.repository.HealthDataRepository
import javax.inject.Inject

class HealthDataRepositoryImpl @Inject constructor(
    private val frecuenciaCardiacaDao: CardiacaDao,
    private val registroActividadDao: RegistroActividadDao,
    private val registroSuenoDao: RegistroSuenoDao,
    private val healthConnectDataSource: HealthConnectDataSource
) : HealthDataRepository {

    //Sincronizae datos de health connect coordinando persistencia de resultados
    override suspend fun sincronizarDatosHealthConnect(inicio: Long, fin: Long) {
        healthConnectDataSource.sincronizarDatos(inicio, fin)
    }

    //Insertar datos nuevos o actualizar existentes
    override suspend fun guardarDatosActividadFisica(datos: List<RegistroActividadEntity>) {
        registroActividadDao.guardarDatosActividadFisica(datos)
    }

    override suspend fun guardarDatosSueno(datos: List<RegistroSuenoEntity>) {
        registroSuenoDao.guardarDatosSueno(datos)
    }

    override suspend fun guardarDatosFrecuenciaCardiaca(datos: List<FrecuenciaCardiacaEntity>) {
        frecuenciaCardiacaDao.guardarDatosFrecuenciaCardiaca(datos)
    }

    //Consultar datos actividad fisica
    override suspend fun obtenerDatosActividadFisica(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroActividadEntity> {
        return registroActividadDao.consultarActividadesPorPeriodo(pacienteId, inicio, fin)
    }

    //Consultar datos de sueño
    override suspend fun obtenerDatosSueno(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroSuenoEntity> {
        return registroSuenoDao.consultarSesionesPorPeriodo(pacienteId, inicio, fin)
    }

    //Consultar datos frecuencia cardiaca
    override suspend fun obtenerDatosFrecuenciaCardiaca(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<FrecuenciaCardiacaEntity> {
        return frecuenciaCardiacaDao.consultarSeriesTemporales(pacienteId, inicio, fin)
    }
}