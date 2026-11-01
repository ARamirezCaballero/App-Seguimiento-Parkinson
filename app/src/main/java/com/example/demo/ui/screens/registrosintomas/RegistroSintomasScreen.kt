package com.example.demo.ui.screens.registrosintomas

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
import com.example.demo.dominio.model.enums.TipoSintoma
import com.example.demo.ui.screens.registrosintomas.components.SeveridadDialog
import com.example.demo.ui.screens.registrosintomas.components.SintomaMotorContent
import com.example.demo.ui.screens.registrosintomas.components.SintomaMotorHeader
import com.example.demo.ui.screens.registrosintomas.components.SintomaNoMotorContent
import com.example.demo.ui.screens.registrosintomas.components.SintomaNoMotorHeader
import com.example.demo.ui.theme.DemoTheme

@Composable
fun RegistroSintomasScreen(
    esMotor: Boolean = true,
    modifier: Modifier = Modifier,
    viewModel: RegistroSintomasViewModel = hiltViewModel(),
    onVolverClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(esMotor) {
        viewModel.setTipoMotor(esMotor)
    }

    RegistroSintomasScreenContent(
        uiState = uiState,
        onVolverClick = onVolverClick,
        onSintomaSelected = viewModel::onSintomaSelected,
        onDismissDialog = viewModel::onDismissDialog,
        onRegistrarSintoma = viewModel::registrarSintoma,
        modifier = modifier
    )
}

@Composable
fun RegistroSintomasScreenContent(
    uiState: RegistroSintomasUiState,
    onVolverClick: () -> Unit,
    onSintomaSelected: (TipoSintoma, Color) -> Unit,
    onDismissDialog: () -> Unit,
    onRegistrarSintoma: (Int) -> Unit,
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
            if (uiState.esMotor) {
                SintomaMotorHeader(
                    onBackClick = onVolverClick
                )
            } else {
                SintomaNoMotorHeader(
                    onBackClick = onVolverClick
                )
            }
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
                if (uiState.esMotor) {
                    SintomaMotorContent(
                        onSintomaClick = onSintomaSelected
                    )
                } else {
                    SintomaNoMotorContent(
                        onSintomaClick = onSintomaSelected
                    )
                }
            }
        }

        uiState.selectedSintoma?.let { sintoma ->
            SeveridadDialog(
                tipoSintoma = sintoma,
                containerColor = uiState.selectedColor,
                onSeveridadSelected = onRegistrarSintoma,
                onCancelarClick = onDismissDialog
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroSintomasMotoresScreenPreview() {
    DemoTheme {
        RegistroSintomasScreenContent(
            uiState = RegistroSintomasUiState(esMotor = true),
            onVolverClick = {},
            onSintomaSelected = { _, _ -> },
            onDismissDialog = {},
            onRegistrarSintoma = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroSintomasNoMotoresScreenPreview() {
    DemoTheme {
        RegistroSintomasScreenContent(
            uiState = RegistroSintomasUiState(esMotor = false),
            onVolverClick = {},
            onSintomaSelected = { _, _ -> },
            onDismissDialog = {},
            onRegistrarSintoma = {}
        )
    }
}
