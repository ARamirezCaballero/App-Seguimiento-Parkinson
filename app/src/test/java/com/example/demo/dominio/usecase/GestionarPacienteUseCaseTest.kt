package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.Paciente
import com.example.demo.dominio.repository.PacienteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GestionarPacienteUseCaseTest {

    private val pacienteRepository: PacienteRepository = mockk()
    private lateinit var useCase: GestionarPacienteUseCase

    @Before
    fun setUp() {
        useCase = GestionarPacienteUseCase(pacienteRepository)
    }

    @After
    fun tearDown() {
        // Garantiza que no hubo ninguna otra llamada no verificada al repositorio
        confirmVerified(pacienteRepository)
    }

    @Test
    fun `obtenerPaciente devuelve el paciente esperado cuando existe en el repositorio`() = runTest {
        // Arrange
        val pacienteEsperado = Paciente(
            id = 1L,
            nombre = "Juan",
            apellido = "Pérez",
            fechaDiagnostico = LocalDate.of(2021, 5, 10)
        )
        coEvery { pacienteRepository.obtenerPaciente() } returns pacienteEsperado

        // Act
        val resultado = useCase.obtenerPaciente()

        // Assert
        assertEquals(
            "El paciente obtenido debe coincidir exactamente con el retornado por el repositorio",
            pacienteEsperado,
            resultado
        )
        coVerify(exactly = 1) { pacienteRepository.obtenerPaciente() }
    }

    @Test
    fun `obtenerPaciente devuelve null sin fallar cuando no hay paciente en la base de datos`() = runTest {
        // Arrange
        coEvery { pacienteRepository.obtenerPaciente() } returns null

        // Act
        val resultado = useCase.obtenerPaciente()

        // Assert
        assertNull(
            "Se esperaba null cuando el repositorio no encuentra ningún paciente registrado",
            resultado
        )
        coVerify(exactly = 1) { pacienteRepository.obtenerPaciente() }
    }

    @Test
    fun `obtenerPaciente propaga excepcion y no enmascara errores si el repositorio falla`() = runTest {
        // Arrange
        val mensajeError = "Error de lectura en la base de datos local"
        coEvery { pacienteRepository.obtenerPaciente() } throws IllegalStateException(mensajeError)

        // Act
        val resultado = runCatching { useCase.obtenerPaciente() }

        // Assert
        assertTrue("La llamada debe resultar en fallo cuando el repositorio lanza una excepción", resultado.isFailure)
        assertEquals(mensajeError, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { pacienteRepository.obtenerPaciente() }
    }

    @Test
    fun `insertarPaciente delega de forma estricta la operacion al repositorio`() = runTest {
        // Arrange
        val nuevoPaciente = Paciente(
            id = 0L,
            nombre = "María",
            apellido = "Gómez",
            fechaDiagnostico = LocalDate.now()
        )
        coEvery { pacienteRepository.insertarPaciente(nuevoPaciente) } returns Unit

        // Act
        useCase.insertarPaciente(nuevoPaciente)

        // Assert
        coVerify(exactly = 1) { pacienteRepository.insertarPaciente(nuevoPaciente) }
    }

    @Test
    fun `insertarPaciente propaga excepcion sin silenciarla si la insercion falla`() = runTest {
        // Arrange
        val nuevoPaciente = Paciente(
            id = 0L,
            nombre = "María",
            apellido = "Gómez",
            fechaDiagnostico = LocalDate.now()
        )
        val errorInsercion = "Violación de restricción UNIQUE en SQLite"
        coEvery { pacienteRepository.insertarPaciente(nuevoPaciente) } throws RuntimeException(errorInsercion)

        // Act
        val resultado = runCatching { useCase.insertarPaciente(nuevoPaciente) }

        // Assert
        assertTrue("La inserción debe resultar en fallo cuando el repositorio lanza una excepción", resultado.isFailure)
        assertEquals(errorInsercion, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { pacienteRepository.insertarPaciente(nuevoPaciente) }
    }

    @Test
    fun `actualizarPaciente delega de forma estricta la operacion al repositorio`() = runTest {
        // Arrange
        val pacienteActualizado = Paciente(
            id = 1L,
            nombre = "Juan Carlos",
            apellido = "Pérez Gómez",
            fechaDiagnostico = LocalDate.of(2021, 5, 10)
        )
        coEvery { pacienteRepository.actualizarPaciente(pacienteActualizado) } returns Unit

        // Act
        useCase.actualizarPaciente(pacienteActualizado)

        // Assert
        coVerify(exactly = 1) { pacienteRepository.actualizarPaciente(pacienteActualizado) }
    }
}