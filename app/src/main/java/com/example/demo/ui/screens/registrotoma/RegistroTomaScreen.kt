package com.example.demo.ui.screens.registrotoma

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.demo.dominio.model.Medicacion
import com.example.demo.dominio.model.PautaMedicacion
import com.example.demo.ui.screens.registrotoma.components.RegistroTomaContent
import com.example.demo.ui.screens.registrotoma.components.RegistroTomaDialog
import com.example.demo.ui.screens.registrotoma.components.RegistroTomaHeader
import com.example.demo.ui.theme.DemoTheme
import java.time.LocalDate

@Composable
fun RegistroTomaScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistroTomaViewModel = hiltViewModel(),
    onVolverClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
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
            RegistroTomaHeader(
                onBackClick = onVolverClick
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF7162FB))
            }
        } else {
            RegistroTomaContent(
                medicamentos = uiState.medicamentosConPauta,
                onMedicamentoClick = viewModel::onMedicamentoSelected,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }

        uiState.selectedMedicamento?.let { item ->
            RegistroTomaDialog(
                item = item,
                hora = uiState.horaRegistrada,
                minuto = uiState.minutoRegistrado,
                onHoraChanged = viewModel::onHoraRegistradaChanged,
                onMinutoChanged = viewModel::onMinutoRegistradoChanged,
                onConfirmarClick = viewModel::confirmarToma,
                onCancelarClick = viewModel::onDismissDialog
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroTomaScreenPreview() {
    DemoTheme {
        val mockItem = MedicamentoConPautaItem(
            medicacion = Medicacion(
                id = 1L,
                pacienteId = 1L,
                principioActivo = "Levodopa",
                nombreComercial = "Levodopa / Carbidopa",
                dosis = "100 mg / 25 mg",
                unidadDosis = "Comprimidos",
                formaFarmaceutica = "Comprimido",
                fechaInicio = LocalDate.now(),
                fechaFin = LocalDate.now().plusYears(1)
            ),
            pauta = PautaMedicacion(
                id = 1L,
                pacienteId = 1L,
                medicacionId = 1L,
                horaInicial = 8,
                intervaloHoras = 8,
                cantidad = 1.0,
                fechaInicio = LocalDate.now(),
                fechaFin = LocalDate.now().plusYears(1),
                activa = true
            )
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White,
            topBar = {
                RegistroTomaHeader(
                    onBackClick = {}
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                RegistroTomaContent(
                    medicamentos = listOf(mockItem),
                    onMedicamentoClick = {}
                )
            }
        }
    }
}
