package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.FrecuenciaCardiaca
import com.example.demo.dominio.model.RegistroActividad
import com.example.demo.dominio.model.RegistroSueno
import com.example.demo.dominio.repository.HealthDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class GestionarMetricasPasivasUseCaseTest {

    private val healthDataRepository: HealthDataRepository = mockk()
    private lateinit var useCase: GestionarMetricasPasivasUseCase

    private val fechaAhora = LocalDateTime.of(2025, 3, 30, 10, 0)

    private val suenoPrueba = RegistroSueno(
        id = 1L,
        pacienteId = 10L,
        inicio = 23,
        fin = 7,
        duracionMinutos = 480.0,
        minutosLigeros = 240,
        minutosProfundos = 120,
        minutosREM = 90,
        minutosDespierto = 30,
        fechaHora = fechaAhora,
        fuente = "Health Connect"
    )

    private val actividadPrueba = RegistroActividad(
        id = 1L,
        pacienteId = 10L,
        inicio = 1000L,
        fin = 2000L,
        pasos = 5000,
        distanciaMetros = 3500.0,
        fechaHora = fechaAhora,
        fuente = "Health Connect"
    )

    private val cardiacaPrueba = FrecuenciaCardiaca(
        id = 1L,
        pacienteId = 10L,
        valor = 72,
        fechaHora = fechaAhora,
        fuente = "Health Connect"
    )

    @Before
    fun setUp() {
        useCase = GestionarMetricasPasivasUseCase(healthDataRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(healthDataRepository)
    }

    @Test
    fun `obtenerDatosSueno devuelve las sesiones de sueno del periodo`() = runTest {
        // Arrange
        val listaSueno = listOf(suenoPrueba)
        coEvery { healthDataRepository.obtenerDatosSueno(10L, 1000L, 2000L) } returns listaSueno

        // Act
        val resultado = useCase.obtenerDatosSueno(10L, 1000L, 2000L)

        // Assert
        assertEquals("Debe retornar las sesiones de sueño esperadas", listaSueno, resultado)
        coVerify(exactly = 1) { healthDataRepository.obtenerDatosSueno(10L, 1000L, 2000L) }
    }

    @Test
    fun `obtenerDatosActividadFisica devuelve los registros de actividad del periodo`() = runTest {
        // Arrange
        val listaActividad = listOf(actividadPrueba)
        coEvery { healthDataRepository.obtenerDatosActividadFisica(10L, 1000L, 2000L) } returns listaActividad

        // Act
        val resultado = useCase.obtenerDatosActividadFisica(10L, 1000L, 2000L)

        // Assert
        assertEquals("Debe retornar los registros de actividad física esperados", listaActividad, resultado)
        coVerify(exactly = 1) { healthDataRepository.obtenerDatosActividadFisica(10L, 1000L, 2000L) }
    }

    @Test
    fun `obtenerDatosFrecuenciaCardiaca devuelve la serie temporal de frecuencia cardiaca`() = runTest {
        // Arrange
        val listaCardiaca = listOf(cardiacaPrueba)
        coEvery { healthDataRepository.obtenerDatosFrecuenciaCardiaca(10L, 1000L, 2000L) } returns listaCardiaca

        // Act
        val resultado = useCase.obtenerDatosFrecuenciaCardiaca(10L, 1000L, 2000L)

        // Assert
        assertEquals("Debe retornar los registros de frecuencia cardíaca esperados", listaCardiaca, resultado)
        coVerify(exactly = 1) { healthDataRepository.obtenerDatosFrecuenciaCardiaca(10L, 1000L, 2000L) }
    }

    @Test
    fun `sincronizarDatosHealthConnect delega la sincronizacion al repositorio`() = runTest {
        // Arrange
        coEvery { healthDataRepository.sincronizarDatosHealthConnect(10L, 1000L, 2000L) } returns Unit

        // Act
        useCase.sincronizarDatosHealthConnect(10L, 1000L, 2000L)

        // Assert
        coVerify(exactly = 1) { healthDataRepository.sincronizarDatosHealthConnect(10L, 1000L, 2000L) }
    }

    @Test
    fun `sincronizarDatosHealthConnect propaga excepcion sin silenciarla cuando la sincronizacion falla`() = runTest {
        // Arrange
        val errorApi = "Error de conexión con Health Connect API"
        coEvery { healthDataRepository.sincronizarDatosHealthConnect(10L, 1000L, 2000L) } throws RuntimeException(errorApi)

        // Act
        val resultado = runCatching { useCase.sincronizarDatosHealthConnect(10L, 1000L, 2000L) }

        // Assert
        assertTrue("La llamada debe resultar en fallo cuando el repositorio lanza excepción", resultado.isFailure)
        assertEquals(errorApi, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { healthDataRepository.sincronizarDatosHealthConnect(10L, 1000L, 2000L) }
    }
}