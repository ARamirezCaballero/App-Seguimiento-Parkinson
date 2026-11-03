package com.example.demo.ui.screens.perfil

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.demo.ui.screens.perfil.components.FormularioPerfilActions
import com.example.demo.ui.screens.perfil.components.PatientInformationForm
import com.example.demo.ui.screens.perfil.components.ProfileHeader
import com.example.demo.ui.theme.DemoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    modifier: Modifier = Modifier,
    viewModel: PerfilViewModel = hiltViewModel(),
    onCancelar: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Al guardar exitosamente el perfil, confirma el guardado y vuelve a inicio
    LaunchedEffect(uiState.mensajeExito, uiState.mensajeError) {
        uiState.mensajeError?.let {
            snackbarHostState.showSnackbar(it)
        }
        uiState.mensajeExito?.let {
            launch { snackbarHostState.showSnackbar(it) }
            delay(1500)
            onCancelar()
        }
    }

    // Estructura de la pantalla Perfil (sin lógica)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            ProfileHeader()
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                PatientInformationForm(
                    nombre = uiState.nombre,
                    onNombreChange = viewModel::onNombreChanged,
                    errorNombre = uiState.errorNombre,
                    apellido = uiState.apellido,
                    onApellidoChange = viewModel::onApellidoChanged,
                    errorApellido = uiState.errorApellido,
                    fechaDiagnostico = uiState.fechaDiagnostico,
                    onFechaDiagnosticoChange = viewModel::onFechaDiagnosticoChanged,
                    errorFechaDiagnostico = uiState.errorFechaDiagnostico
                )

                FormularioPerfilActions(
                    onGuardarClick = viewModel::guardarPerfil,
                    onCancelarClick = onCancelar,
                    isSaving = uiState.isSaving
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    DemoTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White,
            topBar = { ProfileHeader() }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                PatientInformationForm(
                    nombre = "Juan",
                    onNombreChange = {},
                    errorNombre = null,
                    apellido = "Pérez",
                    onApellidoChange = {},
                    errorApellido = null,
                    fechaDiagnostico = "2023-01-15",
                    onFechaDiagnosticoChange = {},
                    errorFechaDiagnostico = null
                )

                FormularioPerfilActions(
                    onGuardarClick = {},
                    onCancelarClick = {},
                    isSaving = false
                )
            }
        }
    }
}
