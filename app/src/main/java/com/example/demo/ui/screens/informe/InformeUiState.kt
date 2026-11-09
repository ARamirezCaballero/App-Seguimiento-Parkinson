package com.example.demo.ui.screens.informe

enum class PeriodoOpcion(val label: String, val dias: Int) {
    ULTIMOS_7_DIAS("Últimos 7 días", 7),
    ULTIMOS_14_DIAS("Últimos 14 días", 14),
    ULTIMOS_30_DIAS("Últimos 30 días", 30),
    ULTIMOS_90_DIAS("Últimos 90 días", 90)
}

data class InformeUiState(
    val periodoSeleccionado: PeriodoOpcion = PeriodoOpcion.ULTIMOS_30_DIAS,
    val isLoading: Boolean = false,
    val snackbarMessage: String? = null,
    val pdfGuardadoRuta: String? = null
)
