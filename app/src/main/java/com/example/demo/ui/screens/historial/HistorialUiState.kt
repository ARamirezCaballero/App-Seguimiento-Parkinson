package com.example.demo.ui.screens.historial

import com.example.demo.dominio.model.ItemHistorial
import com.example.demo.dominio.model.enums.CategoriaHistorial
import java.time.LocalDate

data class HistorialUiState(
    val fechaInicio: LocalDate = LocalDate.now().minusDays(7),
    val fechaFin: LocalDate = LocalDate.now(),
    val categoriaSeleccionada: CategoriaHistorial = CategoriaHistorial.TODOS,
    val itemsHistorial: List<ItemHistorial> = emptyList(),
    val selectedItemForAction: ItemHistorial? = null,
    val editingSeveridad: Int? = null,
    val editingHora: Int? = null,
    val editingMinuto: Int? = null,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val mensajeError: String? = null,
    val mensajeExito: String? = null
)
