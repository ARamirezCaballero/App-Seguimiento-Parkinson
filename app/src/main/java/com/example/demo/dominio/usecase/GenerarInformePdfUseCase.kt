package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.InformeSeguimiento
import com.example.demo.dominio.model.ResumenAdherencia
import com.example.demo.dominio.model.ResumenEstadoMotorYSintomas
import com.example.demo.dominio.model.ResumenSuenoInforme
import com.example.demo.dominio.repository.HealthDataRepository
import com.example.demo.dominio.repository.PacienteRepository
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
import com.example.demo.dominio.repository.RegistroMedicacionRepository
import com.example.demo.dominio.repository.SintomaRepository
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class GenerarInformePdfUseCase @Inject constructor(
    private val pacienteRepository: PacienteRepository,
    private val registroMedicacionRepository: RegistroMedicacionRepository,
    private val sintomaRepository: SintomaRepository,
    private val registroEstadoMotorRepository: RegistroEstadoMotorRepository,
    private val healthDataRepository: HealthDataRepository
) {

    suspend fun generarInforme(
        pacienteId: Long,
        fechaInicio: LocalDate,
        fechaFin: LocalDate
    ): InformeSeguimiento {
        val inicioEpoch = fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val finEpoch = fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        // Datos del paciente
        val paciente = pacienteRepository.obtenerPaciente()
        val nombreCompleto = if (paciente != null) "${paciente.nombre} ${paciente.apellido}".trim() else "Sin información suficiente"
        val fechaDiag = paciente?.fechaDiagnostico?.toString() ?: "Sin información suficiente"

        // Adherencia Farmacologica
        val tomas = registroMedicacionRepository.consultarTomasPorPeriodo(pacienteId, inicioEpoch, finEpoch)
        val adherenciaResumen = if (tomas.isEmpty()) {
            ResumenAdherencia(0, 0, 0, 0.0, "Sin información suficiente")
        } else {
            val aHorario = tomas.count { it.esAHorario() }
            val aDeshorario = tomas.filter { it.esDeshorario() }
            val omitidas = tomas.count { it.esOmitida() }

            val desfasePromedio = if (aDeshorario.isNotEmpty()) {
                aDeshorario.map { it.calcularDesfaseMinutos().toDouble() }.average()
            } else {
                0.0
            }

            ResumenAdherencia(
                tomasAHorario = aHorario,
                tomasDeshorario = aDeshorario.size,
                tomasOmitidas = omitidas,
                desfasePromedioMinutos = desfasePromedio,
                mensaje = "Información de registro manual"
            )
        }

        // Estado Motor y sintomas
        val estadosMotores = registroEstadoMotorRepository.consultarIntervalosPorPeriodo(pacienteId, inicioEpoch, finEpoch)
        val sintomas = sintomaRepository.consultarSintomasPorPeriodo(pacienteId, inicioEpoch, finEpoch)

        val distribucionMotor = if (estadosMotores.isNotEmpty()) {
            val total = estadosMotores.size.toDouble()
            estadosMotores.groupingBy { it.estadoMotor }
                .eachCount()
                .mapValues { (_, count) -> (count / total) * 100.0 }
        } else {
            emptyMap()
        }

        val sintomasMotores = sintomas.filter { it.tipoSintoma.esMotor }
        val sintomasNoMotores = sintomas.filter { !it.tipoSintoma.esMotor }

        val sevMotores = if (sintomasMotores.isNotEmpty()) sintomasMotores.map { it.severidad }.average() else 0.0
        val sevNoMotores = if (sintomasNoMotores.isNotEmpty()) sintomasNoMotores.map { it.severidad }.average() else 0.0

        val maxFreqMotor = if (sintomasMotores.isNotEmpty()) {
            val counts = sintomasMotores.groupingBy { it.tipoSintoma.titulo }.eachCount()
            val maxVal = counts.values.maxOrNull() ?: 0
            counts.filter { it.value == maxVal }.keys.toList()
        } else emptyList()

        val maxFreqNoMotor = if (sintomasNoMotores.isNotEmpty()) {
            val counts = sintomasNoMotores.groupingBy { it.tipoSintoma.titulo }.eachCount()
            val maxVal = counts.values.maxOrNull() ?: 0
            counts.filter { it.value == maxVal }.keys.toList()
        } else emptyList()

        val estadoYSintomasResumen = ResumenEstadoMotorYSintomas(
            distribucionPorcentualMotor = distribucionMotor,
            severidadPromedioMotores = sevMotores,
            severidadPromedioNoMotores = sevNoMotores,
            sintomasMotoresMasFrecuentes = maxFreqMotor,
            sintomasNoMotoresMasFrecuentes = maxFreqNoMotor,
            mensaje = if (estadosMotores.isEmpty() && sintomas.isEmpty()) "Sin información suficiente" else "Información de registro manual"
        )

        // seccion de sueño
        val sesionesSueno = healthDataRepository.obtenerDatosSueno(pacienteId, inicioEpoch, finEpoch)
        val resumenSueno = if (sesionesSueno.isEmpty()) {
            ResumenSuenoInforme(0.0, 0.0, 0.0, 0, "Sin información suficiente")
        } else {
            val totalNoches = sesionesSueno.size.toDouble()
            val duracionProm = sesionesSueno.map { it.duracionMinutos }.average()
            val conInterrupcion = sesionesSueno.count { it.minutosDespierto > 30 }
            val sinInterrupcion = sesionesSueno.size - conInterrupcion

            ResumenSuenoInforme(
                duracionPromedioMinutos = duracionProm,
                porcentajeConInterrupciones = (conInterrupcion / totalNoches) * 100.0,
                porcentajeSinInterrupciones = (sinInterrupcion / totalNoches) * 100.0,
                sesionesCount = sesionesSueno.size,
                mensaje = "Información procedente de Health Connect"
            )
        }

        val tieneDatos = paciente != null && (tomas.isNotEmpty() || estadosMotores.isNotEmpty() || sintomas.isNotEmpty() || sesionesSueno.isNotEmpty())

        return InformeSeguimiento(
            pacienteNombreCompleto = nombreCompleto,
            fechaDiagnostico = fechaDiag,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            adherencia = adherenciaResumen,
            estadoMotorYSintomas = estadoYSintomasResumen,
            resumenSueno = resumenSueno,
            tieneDatosSuficientes = tieneDatos
        )
    }
}