package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.Paciente
import com.example.demo.dominio.model.RegistroEstadoMotor
import com.example.demo.dominio.model.RegistroMedicacion
import com.example.demo.dominio.model.RegistroSintoma
import com.example.demo.dominio.model.RegistroSueno
import com.example.demo.dominio.model.enums.EstadoMedicacion
import com.example.demo.dominio.model.enums.EstadoMotor
import com.example.demo.dominio.model.enums.TipoSintoma
import com.example.demo.dominio.repository.HealthDataRepository
import com.example.demo.dominio.repository.PacienteRepository
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
import com.example.demo.dominio.repository.RegistroMedicacionRepository
import com.example.demo.dominio.repository.SintomaRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class GestionarInformePdfUseCaseTest {

    private val pacienteRepository: PacienteRepository = mockk()
    private val registroMedicacionRepository: RegistroMedicacionRepository = mockk()
    private val sintomaRepository: SintomaRepository = mockk()
    private val registroEstadoMotorRepository: RegistroEstadoMotorRepository = mockk()
    private val healthDataRepository: HealthDataRepository = mockk()

    private lateinit var useCase: GenerarInformePdfUseCase

    private val fechaInicioPrueba = LocalDate.of(2025, 3, 1)
    private val fechaFinPrueba = LocalDate.of(2025, 3, 30)

    @Before
    fun setUp() {
        useCase = GenerarInformePdfUseCase(
            pacienteRepository,
            registroMedicacionRepository,
            sintomaRepository,
            registroEstadoMotorRepository,
            healthDataRepository
        )
    }

    @After
    fun tearDown() {
        confirmVerified(
            pacienteRepository,
            registroMedicacionRepository,
            sintomaRepository,
            registroEstadoMotorRepository,
            healthDataRepository
        )
    }

    @Test
    fun `generarInforme compila el informe completo cuando existen datos registrados`() = runTest {
        // Arrange
        val paciente = Paciente(10L, "Juan", "Pérez", LocalDate.of(2020, 1, 15))
        val toma = RegistroMedicacion(1L, 10L, 5L, LocalDateTime.of(2025, 3, 10, 8, 0), LocalDateTime.of(2025, 3, 10, 8, 0), EstadoMedicacion.TOMADA, 1.0)
        val estadoMotor = RegistroEstadoMotor(1L, 10L, EstadoMotor.ON, LocalDate.of(2025, 3, 10), "Estable")
        val sintoma = RegistroSintoma(1L, 10L, TipoSintoma.Temblor, 2, LocalDate.of(2025, 3, 10))
        val sueno = RegistroSueno(1L, 10L, 23, 7, 480.0, 240, 120, 90, 30, LocalDateTime.of(2025, 3, 10, 7, 0), "HC")

        coEvery { pacienteRepository.obtenerPaciente() } returns paciente
        coEvery { registroMedicacionRepository.consultarTomasPorPeriodo(any(), any(), any()) } returns listOf(toma)
        coEvery { registroEstadoMotorRepository.consultarIntervalosPorPeriodo(any(), any(), any()) } returns listOf(estadoMotor)
        coEvery { sintomaRepository.consultarSintomasPorPeriodo(any(), any(), any()) } returns listOf(sintoma)
        coEvery { healthDataRepository.obtenerDatosSueno(any(), any(), any()) } returns listOf(sueno)

        // Act
        val informe = useCase.generarInforme(10L, fechaInicioPrueba, fechaFinPrueba)

        // Assert
        assertEquals("Juan Pérez", informe.pacienteNombreCompleto)
        assertEquals("2020-01-15", informe.fechaDiagnostico)
        assertTrue("El informe debe indicar que existen datos suficientes", informe.tieneDatosSuficientes)
        assertEquals(1, informe.adherencia.tomasAHorario)

        coVerify(exactly = 1) { pacienteRepository.obtenerPaciente() }
        coVerify(exactly = 1) { registroMedicacionRepository.consultarTomasPorPeriodo(any(), any(), any()) }
        coVerify(exactly = 1) { registroEstadoMotorRepository.consultarIntervalosPorPeriodo(any(), any(), any()) }
        coVerify(exactly = 1) { sintomaRepository.consultarSintomasPorPeriodo(any(), any(), any()) }
        coVerify(exactly = 1) { healthDataRepository.obtenerDatosSueno(any(), any(), any()) }
    }

    @Test
    fun `generarInforme asigna Sin informacion suficiente a las secciones cuando no existen registros`() = runTest {
        // Arrange
        coEvery { pacienteRepository.obtenerPaciente() } returns null
        coEvery { registroMedicacionRepository.consultarTomasPorPeriodo(any(), any(), any()) } returns emptyList()
        coEvery { registroEstadoMotorRepository.consultarIntervalosPorPeriodo(any(), any(), any()) } returns emptyList()
        coEvery { sintomaRepository.consultarSintomasPorPeriodo(any(), any(), any()) } returns emptyList()
        coEvery { healthDataRepository.obtenerDatosSueno(any(), any(), any()) } returns emptyList()

        // Act
        val informe = useCase.generarInforme(10L, fechaInicioPrueba, fechaFinPrueba)

        // Assert
        assertEquals("Sin información suficiente", informe.pacienteNombreCompleto)
        assertEquals("Sin información suficiente", informe.fechaDiagnostico)
        assertEquals("Sin información suficiente", informe.adherencia.mensaje)
        assertEquals("Sin información suficiente", informe.estadoMotorYSintomas.mensaje)
        assertEquals("Sin información suficiente", informe.resumenSueno.mensaje)
        assertFalse("El informe debe indicar que no hay datos suficientes", informe.tieneDatosSuficientes)

        coVerify(exactly = 1) { pacienteRepository.obtenerPaciente() }
        coVerify(exactly = 1) { registroMedicacionRepository.consultarTomasPorPeriodo(any(), any(), any()) }
        coVerify(exactly = 1) { registroEstadoMotorRepository.consultarIntervalosPorPeriodo(any(), any(), any()) }
        coVerify(exactly = 1) { sintomaRepository.consultarSintomasPorPeriodo(any(), any(), any()) }
        coVerify(exactly = 1) { healthDataRepository.obtenerDatosSueno(any(), any(), any()) }
    }

    @Test
    fun `generarInforme propaga excepcion sin silenciarla si la consulta al repositorio falla`() = runTest {
        // Arrange
        val errorDb = "Error de lectura en repositorio de paciente"
        coEvery { pacienteRepository.obtenerPaciente() } throws RuntimeException(errorDb)

        // Act
        val resultado = runCatching { useCase.generarInforme(10L, fechaInicioPrueba, fechaFinPrueba) }

        // Assert
        assertTrue("La llamada debe resultar en fallo ante una excepción del repositorio", resultado.isFailure)
        assertEquals(errorDb, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { pacienteRepository.obtenerPaciente() }
    }
}