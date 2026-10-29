package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.RegistroMedicacion
import com.example.demo.dominio.model.enums.EstadoMedicacion
import com.example.demo.dominio.repository.RegistroMedicacionRepository
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
import java.time.LocalDateTime

class GestionarTomaMedicamentoUseCaseTest {

    private val registroMedicacionRepository: RegistroMedicacionRepository = mockk()
    private lateinit var useCase: GestionarTomaMedicamentoUseCase

    private val fechaProgramadaPrueba = LocalDateTime.of(2025, 3, 30, 8, 0)
    private val fechaRealPrueba = LocalDateTime.of(2025, 3, 30, 8, 15)

    private val tomaPrueba = RegistroMedicacion(
        id = 1L,
        pacienteId = 10L,
        pautaId = 5L,
        fechaHoraReal = fechaRealPrueba,
        fechaHoraProgramada = fechaProgramadaPrueba,
        estado = EstadoMedicacion.TOMADA,
        cantidad = 1.0
    )

    @Before
    fun setUp() {
        useCase = GestionarTomaMedicamentoUseCase(registroMedicacionRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(registroMedicacionRepository)
    }

    @Test
    fun `registrarToma inserta la toma y devuelve el id generado`() = runTest {
        // Arrange
        coEvery { registroMedicacionRepository.registrarToma(tomaPrueba) } returns 100L

        // Act
        val idGenerado = useCase.registrarToma(tomaPrueba)

        // Assert
        assertEquals("El ID generado debe coincidir con el devuelto por el repositorio", 100L, idGenerado)
        coVerify(exactly = 1) { registroMedicacionRepository.registrarToma(tomaPrueba) }
    }

    @Test
    fun `obtenerTomaPorId devuelve el registro de toma cuando existe`() = runTest {
        // Arrange
        coEvery { registroMedicacionRepository.obtenerTomaPorId(1L) } returns tomaPrueba

        // Act
        val resultado = useCase.obtenerTomaPorId(1L)

        // Assert
        assertEquals("La toma obtenida debe coincidir con la registrada", tomaPrueba, resultado)
        coVerify(exactly = 1) { registroMedicacionRepository.obtenerTomaPorId(1L) }
    }

    @Test
    fun `obtenerTomaPorId devuelve null cuando la toma no existe`() = runTest {
        // Arrange
        coEvery { registroMedicacionRepository.obtenerTomaPorId(99L) } returns null

        // Act
        val resultado = useCase.obtenerTomaPorId(99L)

        // Assert
        assertNull("Se esperaba null al consultar una toma inexistente", resultado)
        coVerify(exactly = 1) { registroMedicacionRepository.obtenerTomaPorId(99L) }
    }

    @Test
    fun `consultarTomasPorPeriodo devuelve la lista de tomas en el rango especificado`() = runTest {
        // Arrange
        val listaTomas = listOf(tomaPrueba)
        coEvery { registroMedicacionRepository.consultarTomasPorPeriodo(10L, 1000L, 2000L) } returns listaTomas

        // Act
        val resultado = useCase.consultarTomasPorPeriodo(10L, 1000L, 2000L)

        // Assert
        assertEquals("Debe retornar la lista de tomas del periodo", listaTomas, resultado)
        coVerify(exactly = 1) { registroMedicacionRepository.consultarTomasPorPeriodo(10L, 1000L, 2000L) }
    }

    @Test
    fun `consultarTomasPorPauta devuelve todas las tomas asociadas a una pauta`() = runTest {
        // Arrange
        val listaTomasPauta = listOf(tomaPrueba)
        coEvery { registroMedicacionRepository.consultarTomasPorPauta(5L) } returns listaTomasPauta

        // Act
        val resultado = useCase.consultarTomasPorPauta(5L)

        // Assert
        assertEquals("Debe retornar las tomas asociadas a la pauta dada", listaTomasPauta, resultado)
        coVerify(exactly = 1) { registroMedicacionRepository.consultarTomasPorPauta(5L) }
    }

    @Test
    fun `actualizarToma delega la modificacion al repositorio`() = runTest {
        // Arrange
        coEvery { registroMedicacionRepository.actualizarToma(tomaPrueba) } returns Unit

        // Act
        useCase.actualizarToma(tomaPrueba)

        // Assert
        coVerify(exactly = 1) { registroMedicacionRepository.actualizarToma(tomaPrueba) }
    }

    @Test
    fun `registrarToma propaga excepcion sin silenciarla cuando la insercion falla`() = runTest {
        // Arrange
        val errorDb = "Error al insertar toma en la base de datos"
        coEvery { registroMedicacionRepository.registrarToma(tomaPrueba) } throws RuntimeException(errorDb)

        // Act
        val resultado = runCatching { useCase.registrarToma(tomaPrueba) }

        // Assert
        assertTrue("La llamada debe resultar en fallo cuando el repositorio lanza excepción", resultado.isFailure)
        assertEquals(errorDb, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { registroMedicacionRepository.registrarToma(tomaPrueba) }
    }
}