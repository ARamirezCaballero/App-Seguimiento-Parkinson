package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.AdherenciaMedicacion
import com.example.demo.dominio.model.DistribucionEstadoMotor
import com.example.demo.dominio.model.EstadisticasInterrupcionSueno
import com.example.demo.dominio.model.FrecuenciaCardiaca
import com.example.demo.dominio.model.PuntoSintoma
import com.example.demo.dominio.model.RegistroActividad
import com.example.demo.dominio.model.RegistroSueno
import com.example.demo.dominio.model.RelacionEstadoMedicacion
import com.example.demo.dominio.model.enums.EstadoMedicacion
import com.example.demo.dominio.repository.HealthDataRepository
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
import com.example.demo.dominio.repository.RegistroMedicacionRepository
import com.example.demo.dominio.repository.SintomaRepository
import javax.inject.Inject

// Cálculo de indicadores
class ProcesarIndicadoresUseCase @Inject constructor(
    private val registroEstadoMotorRepository: RegistroEstadoMotorRepository,
    private val sintomaRepository: SintomaRepository,
    private val registroMedicacionRepository: RegistroMedicacionRepository,
    private val healthDataRepository: HealthDataRepository
) {

    //Calcular cantidades y porcentajes de registros de estado motor por periodo
    suspend fun calcularDistribucionEstadoMotor(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): DistribucionEstadoMotor {
        val registros = registroEstadoMotorRepository.consultarIntervalosPorPeriodo(pacienteId, fechaInicio, fechaFin)
        val total = registros.size
        if (total == 0) {
            return DistribucionEstadoMotor(
                conteoPorEstado = emptyMap(),
                porcentajePorEstado = emptyMap(),
                totalRegistros = 0,
                mensaje = "Sin información suficiente"
            )
        }

        val conteo = registros.groupingBy { it.estadoMotor }.eachCount()
        val porcentaje = conteo.mapValues { (_, count) -> (count.toDouble() / total) * 100.0 }

        return DistribucionEstadoMotor(
            conteoPorEstado = conteo,
            porcentajePorEstado = porcentaje,
            totalRegistros = total,
            mensaje = "Total registros: $total"
        )
    }

    //obtener los puntos temporales de los síntomas motores y su severidad por periodo
    suspend fun obtenerSintomasMotoresPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<PuntoSintoma> {
        val registros = sintomaRepository.consultarSintomasPorPeriodo(pacienteId, fechaInicio, fechaFin)
        return registros
            .filter { it.tipoSintoma.esMotor }
            .map {
                PuntoSintoma(
                    id = it.id,
                    tituloSintoma = it.tipoSintoma.titulo,
                    severidad = it.severidad,
                    fechaHora = it.fechaHora,
                    esMotor = true
                )
            }
    }

    //Obtener los puntos temporales de sintomas no motores y su severidad por periodo
    suspend fun obtenerSintomasNoMotoresPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<PuntoSintoma> {
        val registros = sintomaRepository.consultarSintomasPorPeriodo(pacienteId, fechaInicio, fechaFin)
        return registros
            .filter { !it.tipoSintoma.esMotor }
            .map {
                PuntoSintoma(
                    id = it.id,
                    tituloSintoma = it.tipoSintoma.titulo,
                    severidad = it.severidad,
                    fechaHora = it.fechaHora,
                    esMotor = false
                )
            }
    }

    //Calculo de relacion entre estados motores y tomas de medicacion comparados por periodo
    suspend fun calcularRelacionEstadoYMedicacion(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): RelacionEstadoMedicacion {
        val estados = registroEstadoMotorRepository.consultarIntervalosPorPeriodo(pacienteId, fechaInicio, fechaFin)
        val tomas = registroMedicacionRepository.consultarTomasPorPeriodo(pacienteId, fechaInicio, fechaFin)
        val adherencia = calcularAdherenciaMedicacion(pacienteId, fechaInicio, fechaFin)

        val mensajeText = if (estados.isEmpty() && tomas.isEmpty()) "Sin información suficiente" else "Datos procesados"

        return RelacionEstadoMedicacion(
            pacienteId = pacienteId,
            totalEstados = estados.size,
            totalTomas = tomas.size,
            adherencia = adherencia,
            mensaje = mensajeText
        )
    }

    //Calculo de adherencia a la medicacion por periodo
    suspend fun calcularAdherenciaMedicacion(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): AdherenciaMedicacion {
        val tomas = registroMedicacionRepository.consultarTomasPorPeriodo(pacienteId, fechaInicio, fechaFin)
        val total = tomas.size
        if (total == 0) {
            return AdherenciaMedicacion(
                tomasProgramadas = 0,
                tomasRealizadas = 0,
                tomasNoRealizadas = 0,
                porcentajeAdherencia = 0.0,
                mensaje = "Sin información suficiente"
            )
        }

        val realizadas = tomas.count { it.estado == EstadoMedicacion.TOMADA }
        val noRealizadas = tomas.count { it.estado == EstadoMedicacion.NO_TOMADA }
        val porcentaje = (realizadas.toDouble() / total) * 100.0

        return AdherenciaMedicacion(
            tomasProgramadas = total,
            tomasRealizadas = realizadas,
            tomasNoRealizadas = noRealizadas,
            porcentajeAdherencia = porcentaje,
            mensaje = "$porcentaje% adherencia"
        )
    }

    //Obtener evolucion duracion de seuño por periodo
    suspend fun obtenerEvolucionSuenoPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroSueno> {
        return healthDataRepository.obtenerDatosSueno(pacienteId, fechaInicio, fechaFin)
    }

    //Calcular porcentaje de noches con interrupciones y sin interrupcones por periodo
    suspend fun calcularInterrupcionesSueno(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): EstadisticasInterrupcionSueno {
        val sesiones = healthDataRepository.obtenerDatosSueno(pacienteId, fechaInicio, fechaFin)
        val totalNoches = sesiones.size
        if (totalNoches == 0) {
            return EstadisticasInterrupcionSueno(
                nochesTotales = 0,
                nochesConInterrupcion = 0,
                nochesSinInterrupcion = 0,
                porcentajeConInterrupcion = 0.0,
                porcentajeSinInterrupcion = 0.0,
                mensaje = "Sin información suficiente"
            )
        }

        val conInterrupcion = sesiones.count { it.minutosDespierto > 30 }
        val sinInterrupcion = totalNoches - conInterrupcion

        val pctCon = (conInterrupcion.toDouble() / totalNoches) * 100.0
        val pctSin = (sinInterrupcion.toDouble() / totalNoches) * 100.0

        return EstadisticasInterrupcionSueno(
            nochesTotales = totalNoches,
            nochesConInterrupcion = conInterrupcion,
            nochesSinInterrupcion = sinInterrupcion,
            porcentajeConInterrupcion = pctCon,
            porcentajeSinInterrupcion = pctSin,
            mensaje = "$totalNoches noches procesadas"
        )
    }

    //Obtener la serie temporal de actividad por periodo
    suspend fun obtenerSerieActividadPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroActividad> {
        return healthDataRepository.obtenerDatosActividadFisica(pacienteId, fechaInicio, fechaFin)
    }

    //Obtener la serie temporal de frecuencia cardiaca por periodo
    suspend fun obtenerSerieFrecuenciaCardiacaPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<FrecuenciaCardiaca> {
        return healthDataRepository.obtenerDatosFrecuenciaCardiaca(pacienteId, fechaInicio, fechaFin)
    }
}