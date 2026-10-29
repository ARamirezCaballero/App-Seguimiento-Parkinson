package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.RegistroEstadoMotor
import com.example.demo.dominio.model.enums.EstadoMotor
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
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

class GestionarEstadoMotorUseCaseTest {

    private val registroEstadoMotorRepository: RegistroEstadoMotorRepository = mockk()
    private lateinit var useCase: GestionarEstadoMotorUseCase

    private val estadoPrueba = RegistroEstadoMotor(
        id = 1L,
        pacienteId = 10L,
        estadoMotor = EstadoMotor.ON,
        fechaHora = LocalDate.of(2025, 3, 30),
        observacion = "Paciente en estado ON sin complicaciones"
    )

    @Before
    fun setUp() {
        useCase = GestionarEstadoMotorUseCase(registroEstadoMotorRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(registroEstadoMotorRepository)
    }

    @Test
    fun `registrarEstadoMotor inserta el registro y devuelve el id generado`() = runTest {
        // Arrange
        coEvery { registroEstadoMotorRepository.registrarEstadoMotor(estadoPrueba) } returns 20L

        // Act
        val idGenerado = useCase.registrarEstadoMotor(estadoPrueba)

        // Assert
        assertEquals("El ID generado debe coincidir con el devuelto por el repositorio", 20L, idGenerado)
        coVerify(exactly = 1) { registroEstadoMotorRepository.registrarEstadoMotor(estadoPrueba) }
    }

    @Test
    fun `obtenerEstadoMotorPorId devuelve el registro de estado motor cuando existe`() = runTest {
        // Arrange
        coEvery { registroEstadoMotorRepository.obtenerEstadoMotorPorId(1L) } returns estadoPrueba

        // Act
        val resultado = useCase.obtenerEstadoMotorPorId(1L)

        // Assert
        assertEquals("El estado motor obtenido debe coincidir con el registrado", estadoPrueba, resultado)
        coVerify(exactly = 1) { registroEstadoMotorRepository.obtenerEstadoMotorPorId(1L) }
    }

    @Test
    fun `obtenerEstadoMotorPorId devuelve null cuando el registro no existe`() = runTest {
        // Arrange
        coEvery { registroEstadoMotorRepository.obtenerEstadoMotorPorId(99L) } returns null

        // Act
        val resultado = useCase.obtenerEstadoMotorPorId(99L)

        // Assert
        assertNull("Se esperaba null al consultar un id inexistente", resultado)
        coVerify(exactly = 1) { registroEstadoMotorRepository.obtenerEstadoMotorPorId(99L) }
    }

    @Test
    fun `consultarIntervalosPorPeriodo devuelve los estados motores en el rango especificado`() = runTest {
        // Arrange
        val listaEstados = listOf(estadoPrueba)
        coEvery { registroEstadoMotorRepository.consultarIntervalosPorPeriodo(10L, 1000L, 2000L) } returns listaEstados

        // Act
        val resultado = useCase.consultarIntervalosPorPeriodo(10L, 1000L, 2000L)

        // Assert
        assertEquals("Debe retornar la lista de estados motores del periodo", listaEstados, resultado)
        coVerify(exactly = 1) { registroEstadoMotorRepository.consultarIntervalosPorPeriodo(10L, 1000L, 2000L) }
    }

    @Test
    fun `actualizarEstadoMotor delega la modificacion al repositorio`() = runTest {
        // Arrange
        coEvery { registroEstadoMotorRepository.actualizarEstadoMotor(estadoPrueba) } returns Unit

        // Act
        useCase.actualizarEstadoMotor(estadoPrueba)

        // Assert
        coVerify(exactly = 1) { registroEstadoMotorRepository.actualizarEstadoMotor(estadoPrueba) }
    }

    @Test
    fun `eliminarEstadoMotor delega la eliminacion por objeto al repositorio`() = runTest {
        // Arrange
        coEvery { registroEstadoMotorRepository.eliminarEstadoMotor(estadoPrueba) } returns Unit

        // Act
        useCase.eliminarEstadoMotor(estadoPrueba)

        // Assert
        coVerify(exactly = 1) { registroEstadoMotorRepository.eliminarEstadoMotor(estadoPrueba) }
    }

    @Test
    fun `eliminarEstadoMotorPorId delega la eliminacion por id al repositorio`() = runTest {
        // Arrange
        coEvery { registroEstadoMotorRepository.eliminarEstadoMotorPorId(1L) } returns Unit

        // Act
        useCase.eliminarEstadoMotorPorId(1L)

        // Assert
        coVerify(exactly = 1) { registroEstadoMotorRepository.eliminarEstadoMotorPorId(1L) }
    }

    @Test
    fun `registrarEstadoMotor propaga excepcion sin silenciarla cuando la insercion falla`() = runTest {
        // Arrange
        val errorDb = "Error al insertar estado motor en la base de datos"
        coEvery { registroEstadoMotorRepository.registrarEstadoMotor(estadoPrueba) } throws RuntimeException(errorDb)

        // Act
        val resultado = runCatching { useCase.registrarEstadoMotor(estadoPrueba) }

        // Assert
        assertTrue("La llamada debe resultar en fallo cuando el repositorio lanza excepción", resultado.isFailure)
        assertEquals(errorDb, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { registroEstadoMotorRepository.registrarEstadoMotor(estadoPrueba) }
    }
}