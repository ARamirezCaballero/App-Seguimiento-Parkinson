package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.FrecuenciaCardiaca
import com.example.demo.dominio.model.RegistroActividad
import com.example.demo.dominio.model.RegistroSueno
import com.example.demo.dominio.repository.HealthDataRepository
import javax.inject.Inject

class GestionarMetricasPasivasUseCase @Inject constructor(
    private val healthDataRepository: HealthDataRepository
) {

    //Recuperar registros de sueño del periodo solicitado
    suspend fun obtenerDatosSueno(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroSueno> {
        return healthDataRepository.obtenerDatosSueno(pacienteId, inicio, fin)
    }

    //Recuperar los reistros de actividad del período solicitado
    suspend fun obtenerDatosActividadFisica(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<RegistroActividad> {
        return healthDataRepository.obtenerDatosActividadFisica(pacienteId, inicio, fin)
    }

    //Recuperar las mediciones de frecuencia cardíaca del período solicitado
    suspend fun obtenerDatosFrecuenciaCardiaca(
        pacienteId: Long,
        inicio: Long,
        fin: Long
    ): List<FrecuenciaCardiaca> {
        return healthDataRepository.obtenerDatosFrecuenciaCardiaca(pacienteId, inicio, fin)
    }

    //Solicitar la actualizacion de los datos desde Health Connect y conservar los registros localmente
    suspend fun sincronizarDatosHealthConnect(pacienteId: Long, inicio: Long, fin: Long) {
        healthDataRepository.sincronizarDatosHealthConnect(pacienteId, inicio, fin)
    }
}