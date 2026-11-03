package com.example.demo.ui.screens.registrotoma

import com.example.demo.dominio.model.Medicacion
import com.example.demo.dominio.model.PautaMedicacion

import java.time.LocalDateTime

data class MedicamentoConPautaItem(
    val medicacion: Medicacion,
    val pauta: PautaMedicacion?
) {
    val displayNombreYDosis: String
        get() = if (medicacion.dosis.isNotBlank()) "${medicacion.nombreComercial} ${medicacion.dosis}" else medicacion.nombreComercial
}

data class RegistroTomaUiState(
    val isLoading: Boolean = false,
    val medicamentosConPauta: List<MedicamentoConPautaItem> = emptyList(),
    val selectedMedicamento: MedicamentoConPautaItem? = null,
    val fechaHoraProgramadaSugerida: LocalDateTime? = null,
    val horaRegistrada: Int = 8,
    val minutoRegistrado: Int = 0,
    val isSaving: Boolean = false,
    val mensajeError: String? = null,
    val mensajeExito: String? = null
)
