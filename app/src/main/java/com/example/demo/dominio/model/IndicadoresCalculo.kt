package com.example.demo.dominio.model

import com.example.demo.dominio.model.enums.EstadoMotor
import java.time.LocalDate

data class DistribucionEstadoMotor(
    val conteoPorEstado: Map<EstadoMotor, Int> = emptyMap(),
    val porcentajePorEstado: Map<EstadoMotor, Double> = emptyMap(),
    val totalRegistros: Int = 0,
    val mensaje: String = if (totalRegistros == 0) "Sin información suficiente" else "Total registros: $totalRegistros"
)

data class PuntoSintoma(
    val id: Long,
    val tituloSintoma: String,
    val severidad: Int,
    val fechaHora: LocalDate,
    val esMotor: Boolean
)

data class AdherenciaMedicacion(
    val tomasProgramadas: Int = 0,
    val tomasRealizadas: Int = 0,
    val tomasNoRealizadas: Int = 0,
    val porcentajeAdherencia: Double = 0.0,
    val mensaje: String = if (tomasProgramadas == 0) "Sin información suficiente" else "$porcentajeAdherencia% adherencia"
)

data class EstadisticasInterrupcionSueno(
    val nochesTotales: Int = 0,
    val nochesConInterrupcion: Int = 0,
    val nochesSinInterrupcion: Int = 0,
    val porcentajeConInterrupcion: Double = 0.0,
    val porcentajeSinInterrupcion: Double = 0.0,
    val mensaje: String = if (nochesTotales == 0) "Sin información suficiente" else "$nochesTotales noches procesadas"
)

data class RelacionEstadoMedicacion(
    val pacienteId: Long,
    val totalEstados: Int = 0,
    val totalTomas: Int = 0,
    val adherencia: AdherenciaMedicacion = AdherenciaMedicacion(),
    val mensaje: String = if (totalEstados == 0 && totalTomas == 0) "Sin información suficiente" else "Datos procesados"
)

data class IndicadoresCalculo(
    val distribucionEstadoMotor: DistribucionEstadoMotor,
    val sintomasMotores: List<PuntoSintoma>,
    val sintomasNoMotores: List<PuntoSintoma>,
    val relacionEstadoMedicacion: RelacionEstadoMedicacion,
    val adherenciaMedicacion: AdherenciaMedicacion,
    val evolucionSueno: List<RegistroSueno>,
    val interrupcionSueno: EstadisticasInterrupcionSueno,
    val serieActividad: List<RegistroActividad>,
    val serieFrecuenciaCardiaca: List<FrecuenciaCardiaca>
)