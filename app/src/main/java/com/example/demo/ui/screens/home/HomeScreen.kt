package com.example.demo.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.demo.ui.screens.home.components.HomeHeader
import com.example.demo.ui.screens.home.components.HomeNavigationSection
import com.example.demo.ui.screens.home.components.HomeTitle
import com.example.demo.ui.screens.home.components.MedicationSection
import com.example.demo.ui.screens.home.components.QuickAccessCard
import com.example.demo.ui.screens.home.components.SyncStatus
import com.example.demo.ui.theme.DemoTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToPerfil: () -> Unit = {},
    onNavigateToRegistroSintomas: () -> Unit = {},
    onNavigateToRegistroEstadoMotor: () -> Unit = {},
    onNavigateToRegistroToma: () -> Unit = {},
    onNavigateToConfiguracionMedicacion: () -> Unit = {},
    onNavigateToEvolucion: () -> Unit = {},
    onNavigateToInforme: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        modifier = modifier,
        onNavigateToPerfil = onNavigateToPerfil,
        onNavigateToRegistroSintomas = onNavigateToRegistroSintomas,
        onNavigateToRegistroEstadoMotor = onNavigateToRegistroEstadoMotor,
        onNavigateToRegistroToma = onNavigateToRegistroToma,
        onNavigateToConfiguracionMedicacion = onNavigateToConfiguracionMedicacion,
        onNavigateToEvolucion = onNavigateToEvolucion,
        onNavigateToInforme = onNavigateToInforme
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    onNavigateToPerfil: () -> Unit = {},
    onNavigateToRegistroSintomas: () -> Unit = {},
    onNavigateToRegistroEstadoMotor: () -> Unit = {},
    onNavigateToRegistroToma: () -> Unit = {},
    onNavigateToConfiguracionMedicacion: () -> Unit = {},
    onNavigateToEvolucion: () -> Unit = {},
    onNavigateToInforme: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            HomeHeader(onPerfilClick = onNavigateToPerfil)
        },
        bottomBar = {
            HomeNavigationSection(
                onEvolucionClick = onNavigateToEvolucion,
                onInformeClick = onNavigateToInforme
            )
        }
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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HomeTitle()

                QuickAccessCard(
                    onRegistrarSintomaClick = onNavigateToRegistroSintomas,
                    onRegistrarEstadoMotorClick = onNavigateToRegistroEstadoMotor
                )

                MedicationSection(
                    onRegistrarTomaClick = onNavigateToRegistroToma,
                    onAddMedicamentoClick = onNavigateToConfiguracionMedicacion
                )

                SyncStatus(lastSyncDate = uiState.lastSyncDate)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DemoTheme {
        HomeScreenContent(
            uiState = HomeUiState(
                lastSyncDate = "31/10/2026 14:30",
                hasHealthPermissions = true,
                isLoading = false
            )
        )
    }
}
