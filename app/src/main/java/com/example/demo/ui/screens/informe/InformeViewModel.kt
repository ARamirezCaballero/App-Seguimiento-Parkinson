package com.example.demo.ui.screens.informe

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.dominio.repository.PacienteRepository
import com.example.demo.dominio.usecase.GenerarInformePdfUseCase
import com.example.demo.util.PdfExporter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class InformeViewModel @Inject constructor(
    private val generarInformePdfUseCase: GenerarInformePdfUseCase,
    private val pacienteRepository: PacienteRepository,
    private val pdfExporter: PdfExporter,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(InformeUiState())
    val uiState: StateFlow<InformeUiState> = _uiState.asStateFlow()

    fun seleccionarPeriodo(periodo: PeriodoOpcion) {
        _uiState.update { it.copy(periodoSeleccionado = periodo) }
    }

    fun clearSnackbarMessage() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun generarInformePdf() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, snackbarMessage = null) }

            try {
                val periodo = _uiState.value.periodoSeleccionado
                val fechaFin = LocalDate.now()
                val fechaInicio = fechaFin.minusDays(periodo.dias.toLong())

                val paciente = pacienteRepository.obtenerPaciente()
                val pacienteId = paciente?.id ?: 1L

                // 1. Compilar datos mediante el Caso de Uso de Dominio
                val informe = generarInformePdfUseCase.generarInforme(
                    pacienteId = pacienteId,
                    fechaInicio = fechaInicio,
                    fechaFin = fechaFin
                )

                // 2. Guardar archivo PDF directamente en la carpeta pública de Descargas (Downloads) del almacenamiento interno
                val nombreArchivo = "Informe_Seguimiento_${fechaInicio}_a_${fechaFin}.pdf"
                val rutaGuardada = guardarPdfEnDescargas(nombreArchivo) { outputStream ->
                    pdfExporter.exportarPdf(informe, outputStream)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        pdfGuardadoRuta = rutaGuardada,
                        snackbarMessage = "Informe guardado exitosamente en Descargas ($nombreArchivo)"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        snackbarMessage = "Error al generar el informe: ${e.localizedMessage ?: "Error desconocido"}"
                    )
                }
            }
        }
    }

    private fun guardarPdfEnDescargas(
        nombreArchivo: String,
        writeBlock: (java.io.OutputStream) -> Unit
    ): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, nombreArchivo)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            if (uri != null) {
                resolver.openOutputStream(uri)?.use { outputStream ->
                    writeBlock(outputStream)
                }
                return "Descargas/$nombreArchivo"
            }
        }

        // Fallback para almacenamiento directo en directorio de descargas públicas
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }
        val file = File(downloadsDir, nombreArchivo)
        FileOutputStream(file).use { outputStream ->
            writeBlock(outputStream)
        }
        return file.absolutePath
    }
}
