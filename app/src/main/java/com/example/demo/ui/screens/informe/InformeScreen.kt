package com.example.demo.ui.screens.informe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.demo.ui.screens.informe.components.GenerateReportButton
import com.example.demo.ui.screens.informe.components.InformeHeader
import com.example.demo.ui.screens.informe.components.ReportContentSummary
import com.example.demo.ui.screens.informe.components.ReportGenerationStatus
import com.example.demo.ui.screens.informe.components.ReportPeriodSelector

@Composable
fun InformeScreen(
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InformeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearSnackbarMessage()
        }
    }

    Scaffold(
        topBar = {
            InformeHeader(
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            ReportGenerationStatus(
                snackbarHostState = snackbarHostState
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
            ReportPeriodSelector(
                periodoSeleccionado = uiState.periodoSeleccionado,
                onPeriodoSelected = { periodo ->
                    viewModel.seleccionarPeriodo(periodo)
                }
            )

            ReportContentSummary()

            Spacer(modifier = Modifier.height(16.dp))

            GenerateReportButton(
                isLoading = uiState.isLoading,
                onClick = {
                    viewModel.generarInformePdf()
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
