package com.example.demo.ui.screens.evolucion

import com.example.demo.dominio.model.AdherenciaMedicacion
import com.example.demo.dominio.model.DistribucionEstadoMotor
import com.example.demo.dominio.model.EstadisticasInterrupcionSueno
import com.example.demo.dominio.model.PuntoSintoma
import com.example.demo.dominio.model.RegistroSueno
import com.example.demo.ui.screens.informe.PeriodoOpcion

data class EvolucionUiState(
    val periodoSeleccionado: PeriodoOpcion = PeriodoOpcion.ULTIMOS_30_DIAS,
    val isLoading: Boolean = false,
    val distribucionEstadoMotor: DistribucionEstadoMotor? = null,
    val sintomasMotores: List<PuntoSintoma> = emptyList(),
    val sintomasNoMotores: List<PuntoSintoma> = emptyList(),
    val adherenciaMedicacion: AdherenciaMedicacion? = null,
    val interrupcionSueno: EstadisticasInterrupcionSueno? = null,
    val registrosSueno: List<RegistroSueno> = emptyList()
)
