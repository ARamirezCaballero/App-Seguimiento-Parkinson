package com.example.demo.ui.screens.registroestadomotor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.demo.dominio.model.enums.EstadoMotor
import com.example.demo.ui.screens.registroestadomotor.components.EstadoMotorContent
import com.example.demo.ui.screens.registroestadomotor.components.EstadoMotorDialog
import com.example.demo.ui.screens.registroestadomotor.components.EstadoMotorHeader
import com.example.demo.ui.theme.DemoTheme

@Composable
fun RegistroEstadoMotorScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistroEstadoMotorViewModel = hiltViewModel(),
    onVolverClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    RegistroEstadoMotorScreenContent(
        uiState = uiState,
        onVolverClick = onVolverClick,
        onEstadoMotorClick = viewModel::onEstadoMotorSelected,
        onHoraChanged = viewModel::onHoraChanged,
        onMinutoChanged = viewModel::onMinutoChanged,
        onGuardarClick = viewModel::registrarEstadoMotor,
        onDismissDialog = viewModel::onDismissDialog,
        modifier = modifier
    )
}

@Composable
fun RegistroEstadoMotorScreenContent(
    uiState: RegistroEstadoMotorUiState,
    onVolverClick: () -> Unit,
    onEstadoMotorClick: (EstadoMotor, Color) -> Unit,
    onHoraChanged: (Int) -> Unit,
    onMinutoChanged: (Int) -> Unit,
    onGuardarClick: () -> Unit,
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
            EstadoMotorHeader(
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
            ) {
                EstadoMotorContent(
                    onEstadoMotorClick = onEstadoMotorClick
                )
            }
        }

        uiState.selectedEstadoMotor?.let { estado ->
            EstadoMotorDialog(
                estadoMotor = estado,
                containerColor = uiState.selectedColor,
                hora = uiState.horaRegistrada,
                minuto = uiState.minutoRegistrado,
                onHoraChanged = onHoraChanged,
                onMinutoChanged = onMinutoChanged,
                onGuardarClick = onGuardarClick,
                onCancelarClick = onDismissDialog
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroEstadoMotorScreenPreview() {
    DemoTheme {
        RegistroEstadoMotorScreenContent(
            uiState = RegistroEstadoMotorUiState(),
            onVolverClick = {},
            onEstadoMotorClick = { _, _ -> },
            onHoraChanged = {},
            onMinutoChanged = {},
            onGuardarClick = {},
            onDismissDialog = {}
        )
    }
}
