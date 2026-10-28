package com.example.demo.dominio.model

import com.example.demo.dominio.model.enums.EstadoMotor
import java.time.LocalDate

data class ResumenAdherencia(
    val tomasAHorario: Int = 0,
    val tomasDeshorario: Int = 0,
    val tomasOmitidas: Int = 0,
    val desfasePromedioMinutos: Double = 0.0,
    val mensaje: String = if (tomasAHorario == 0 && tomasDeshorario == 0 && tomasOmitidas == 0) "Sin información suficiente" else "Información de registro manual"
)

data class ResumenEstadoMotorYSintomas(
    val distribucionPorcentualMotor: Map<EstadoMotor, Double> = emptyMap(),
    val severidadPromedioMotores: Double = 0.0,
    val severidadPromedioNoMotores: Double = 0.0,
    val sintomasMotoresMasFrecuentes: List<String> = emptyList(),
    val sintomasNoMotoresMasFrecuentes: List<String> = emptyList(),
    val mensaje: String = if (distribucionPorcentualMotor.isEmpty() && sintomasMotoresMasFrecuentes.isEmpty() && sintomasNoMotoresMasFrecuentes.isEmpty()) "Sin información suficiente" else "Información de registro manual"
)

data class ResumenSuenoInforme(
    val duracionPromedioMinutos: Double = 0.0,
    val porcentajeConInterrupciones: Double = 0.0,
    val porcentajeSinInterrupciones: Double = 0.0,
    val sesionesCount: Int = 0,
    val mensaje: String = if (sesionesCount == 0) "Sin información suficiente" else "Información procedente de Health Connect"
)

data class InformeSeguimiento(
    val pacienteNombreCompleto: String = "Sin información suficiente",
    val fechaDiagnostico: String = "Sin información suficiente",
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val adherencia: ResumenAdherencia = ResumenAdherencia(),
    val estadoMotorYSintomas: ResumenEstadoMotorYSintomas = ResumenEstadoMotorYSintomas(),
    val resumenSueno: ResumenSuenoInforme = ResumenSuenoInforme(),
    val tieneDatosSuficientes: Boolean = false
)