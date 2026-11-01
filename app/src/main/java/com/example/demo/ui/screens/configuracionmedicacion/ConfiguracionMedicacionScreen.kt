package com.example.demo.ui.screens.configuracionmedicacion

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
import com.example.demo.ui.screens.configuracionmedicacion.components.ConfiguracionMedicacionContent
import com.example.demo.ui.screens.configuracionmedicacion.components.ConfiguracionMedicacionHeader
import com.example.demo.ui.theme.DemoTheme

@Composable
fun ConfiguracionMedicacionScreen(
    modifier: Modifier = Modifier,
    viewModel: ConfiguracionMedicacionViewModel = hiltViewModel(),
    onVolverClick: () -> Unit = {},
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
            ConfiguracionMedicacionHeader(
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                ConfiguracionMedicacionContent(
                    uiState = uiState,
                    onNombreMedicacionChanged = viewModel::onNombreMedicacionChanged,
                    onPrincipioActivoChanged = viewModel::onPrincipioActivoChanged,
                    onDosisGeneralChanged = viewModel::onDosisGeneralChanged,
                    onDosisUnitariaChanged = viewModel::onDosisUnitariaChanged,
                    onFormaFarmaceuticaChanged = viewModel::onFormaFarmaceuticaChanged,
                    onFechaInicioChanged = viewModel::onFechaInicioChanged,
                    onFechaFinChanged = viewModel::onFechaFinChanged,
                    onUnidadAdministracionChanged = viewModel::onUnidadAdministracionChanged,
                    onIntervaloAdministracionChanged = viewModel::onIntervaloAdministracionChanged,
                    onHoraInicioChanged = viewModel::onHoraInicioChanged,
                    onShowFechaInicioPickerChanged = viewModel::onShowFechaInicioPickerChanged,
                    onShowFechaFinPickerChanged = viewModel::onShowFechaFinPickerChanged,
                    onShowHoraInicioPickerChanged = viewModel::onShowHoraInicioPickerChanged,
                    onGuardarClick = viewModel::guardarMedicamento,
                    onCancelarClick = onVolverClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfiguracionMedicacionScreenPreview() {
    DemoTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White,
            topBar = {
                ConfiguracionMedicacionHeader(
                    onBackClick = {}
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                ConfiguracionMedicacionContent(
                    uiState = ConfiguracionMedicacionUiState(
                        nombreMedicacion = "Levodopa / Carbidopa",
                        principioActivo = "Levodopa",
                        dosisGeneral = "100 mg / 25 mg",
                        dosisUnitaria = "1",
                        formaFarmaceutica = "Comprimido",
                        fechaInicio = "2026-03-01",
                        fechaFin = "2027-03-01",
                        unidadAdministracion = "Comprimidos",
                        intervaloAdministracion = "8",
                        horaInicio = "08 : 00"
                    ),
                    onNombreMedicacionChanged = {},
                    onPrincipioActivoChanged = {},
                    onDosisGeneralChanged = {},
                    onDosisUnitariaChanged = {},
                    onFormaFarmaceuticaChanged = {},
                    onFechaInicioChanged = {},
                    onFechaFinChanged = {},
                    onUnidadAdministracionChanged = {},
                    onIntervaloAdministracionChanged = {},
                    onHoraInicioChanged = {},
                    onShowFechaInicioPickerChanged = {},
                    onShowFechaFinPickerChanged = {},
                    onShowHoraInicioPickerChanged = {},
                    onGuardarClick = {},
                    onCancelarClick = {}
                )
            }
        }
    }
}
