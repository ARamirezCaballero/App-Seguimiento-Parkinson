package com.example.demo.ui.screens.historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.demo.dominio.model.ItemHistorial
import com.example.demo.dominio.model.enums.CategoriaHistorial
import com.example.demo.dominio.model.enums.TipoRegistroHistorial
import com.example.demo.ui.screens.historial.components.EmptyHistorialMessage
import com.example.demo.ui.screens.historial.components.HistorialActionDialog
import com.example.demo.ui.screens.historial.components.HistorialCategoryFilterRow
import com.example.demo.ui.screens.historial.components.HistorialHeader
import com.example.demo.ui.screens.historial.components.HistorialItemCard
import com.example.demo.ui.screens.historial.components.HistorialPeriodSelector
import com.example.demo.ui.theme.DemoTheme
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun HistorialScreen(
    modifier: Modifier = Modifier,
    viewModel: HistorialViewModel = hiltViewModel(),
    onVolverClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HistorialScreenContent(
        uiState = uiState,
        onVolverClick = onVolverClick,
        onCategoriaSelected = viewModel::onCategoriaSelected,
        onPeriodoSelected = viewModel::onPeriodoSelected,
        onItemClick = viewModel::onItemClick,
        onEliminarItem = viewModel::eliminarItemSeleccionado,
        onDismissDialog = viewModel::onDismissActionDialog,
        modifier = modifier
    )
}

@Composable
fun HistorialScreenContent(
    uiState: HistorialUiState,
    onVolverClick: () -> Unit,
    onCategoriaSelected: (CategoriaHistorial) -> Unit,
    onPeriodoSelected: (LocalDate, LocalDate) -> Unit,
    onItemClick: (ItemHistorial) -> Unit,
    onEliminarItem: () -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.mensajeExito, uiState.mensajeError) {
        uiState.mensajeExito?.let {
            snackbarHostState.showSnackbar(it)
        }
        uiState.mensajeError?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            HistorialHeader(
                onBackClick = onVolverClick
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Selector de fecha/período
            HistorialPeriodSelector(
                fechaInicio = uiState.fechaInicio,
                fechaFin = uiState.fechaFin,
                onPeriodoSelected = onPeriodoSelected
            )

            // Selector para filtrar según categoría deseada
            Text(
                text = "Filtrar Historial",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Color(0xFF4C0099),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            HistorialCategoryFilterRow(
                categoriaSeleccionada = uiState.categoriaSeleccionada,
                onCategoriaSelected = onCategoriaSelected
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF7162FB))
                }
            } else if (uiState.itemsHistorial.isEmpty()) {
                // Avisa si hay un período sin registros
                EmptyHistorialMessage(
                    modifier = Modifier.weight(1f)
                )
            } else {
                // Lista ordenada cronológicamente con las entradas registradas
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.itemsHistorial) { item ->
                        HistorialItemCard(
                            item = item,
                            onClick = { onItemClick(item) }
                        )
                    }
                }
            }
        }

        // Diálogo con acciones para el elemento seleccionado
        uiState.selectedItemForAction?.let { item ->
            HistorialActionDialog(
                item = item,
                onEliminarClick = onEliminarItem,
                onCancelarClick = onDismissDialog
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistorialScreenPreview() {
    DemoTheme {
        val mockItem = ItemHistorial(
            id = 1L,
            nombre = "Nombre Medicamento - 100mg",
            detalles = "Hora programada: 08:00 hs",
            categoria = CategoriaHistorial.MEDICAMENTOS,
            tipoRegistro = TipoRegistroHistorial.TOMA_MEDICACION,
            fechaHora = LocalDateTime.now(),
            origenDatos = "Registro manual",
            referenciaId = 1L
        )

        HistorialScreenContent(
            uiState = HistorialUiState(
                itemsHistorial = listOf(mockItem)
            ),
            onVolverClick = {},
            onCategoriaSelected = {},
            onPeriodoSelected = { _, _ -> },
            onItemClick = {},
            onEliminarItem = {},
            onDismissDialog = {}
        )
    }
}
