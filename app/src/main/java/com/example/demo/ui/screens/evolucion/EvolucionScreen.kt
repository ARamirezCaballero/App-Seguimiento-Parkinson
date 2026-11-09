package com.example.demo.ui.screens.evolucion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.demo.ui.screens.evolucion.components.AnalysisPeriodSelector
import com.example.demo.ui.screens.evolucion.components.EvolucionHeader
import com.example.demo.ui.screens.evolucion.components.MedicationAdherenceChart
import com.example.demo.ui.screens.evolucion.components.MotorStateDistributionChart
import com.example.demo.ui.screens.evolucion.components.MotorSymptomTrendChart
import com.example.demo.ui.screens.evolucion.components.NonMotorSymptomTrendChart
import com.example.demo.ui.screens.evolucion.components.SleepTrendChart

@Composable
fun EvolucionScreen(
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: EvolucionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            EvolucionHeader(
                onBackClick = onBackClick
            )
        },
        containerColor = Color.White,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            AnalysisPeriodSelector(
                periodoSeleccionado = uiState.periodoSeleccionado,
                onPeriodoSelected = { periodo ->
                    viewModel.seleccionarPeriodo(periodo)
                }
            )

            MotorStateDistributionChart(
                distribucion = uiState.distribucionEstadoMotor
            )

            MotorSymptomTrendChart(
                sintomasMotores = uiState.sintomasMotores
            )

            NonMotorSymptomTrendChart(
                sintomasNoMotores = uiState.sintomasNoMotores
            )

            SleepTrendChart(
                interrupcionSueno = uiState.interrupcionSueno,
                registrosSueno = uiState.registrosSueno
            )

            MedicationAdherenceChart(
                adherencia = uiState.adherenciaMedicacion
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
