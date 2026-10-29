package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.RegistroSintoma
import com.example.demo.dominio.model.enums.TipoSintoma
import com.example.demo.dominio.repository.SintomaRepository
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

class GestionarSintomaUseCaseTest {

    private val sintomaRepository: SintomaRepository = mockk()
    private lateinit var useCase: GestionarSintomaUseCase

    private val sintomaPrueba = RegistroSintoma(
        id = 1L,
        pacienteId = 10L,
        tipoSintoma = TipoSintoma.Temblor,
        severidad = 3,
        fechaHora = LocalDate.of(2025, 3, 30)
    )

    @Before
    fun setUp() {
        useCase = GestionarSintomaUseCase(sintomaRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(sintomaRepository)
    }

    @Test
    fun `registrarSintoma inserta el sintoma y devuelve el id generado`() = runTest {
        // Arrange
        coEvery { sintomaRepository.registrarSintoma(sintomaPrueba) } returns 15L

        // Act
        val idGenerado = useCase.registrarSintoma(sintomaPrueba)

        // Assert
        assertEquals("El ID generado debe coincidir con el devuelto por el repositorio", 15L, idGenerado)
        coVerify(exactly = 1) { sintomaRepository.registrarSintoma(sintomaPrueba) }
    }

    @Test
    fun `obtenerSintomaPorId devuelve el registro de sintoma cuando existe`() = runTest {
        // Arrange
        coEvery { sintomaRepository.obtenerSintomaPorId(1L) } returns sintomaPrueba

        // Act
        val resultado = useCase.obtenerSintomaPorId(1L)

        // Assert
        assertEquals("El síntoma obtenido debe coincidir con el registrado", sintomaPrueba, resultado)
        coVerify(exactly = 1) { sintomaRepository.obtenerSintomaPorId(1L) }
    }

    @Test
    fun `obtenerSintomaPorId devuelve null cuando el sintoma no existe`() = runTest {
        // Arrange
        coEvery { sintomaRepository.obtenerSintomaPorId(99L) } returns null

        // Act
        val resultado = useCase.obtenerSintomaPorId(99L)

        // Assert
        assertNull("Se esperaba null al consultar un id inexistente", resultado)
        coVerify(exactly = 1) { sintomaRepository.obtenerSintomaPorId(99L) }
    }

    @Test
    fun `consultarSintomasPorPeriodo devuelve los sintomas en el rango especificado`() = runTest {
        // Arrange
        val listaSintomas = listOf(sintomaPrueba)
        coEvery { sintomaRepository.consultarSintomasPorPeriodo(10L, 1000L, 2000L) } returns listaSintomas

        // Act
        val resultado = useCase.consultarSintomasPorPeriodo(10L, 1000L, 2000L)

        // Assert
        assertEquals("Debe retornar la lista de síntomas del periodo", listaSintomas, resultado)
        coVerify(exactly = 1) { sintomaRepository.consultarSintomasPorPeriodo(10L, 1000L, 2000L) }
    }

    @Test
    fun `actualizarSintoma delega la modificacion al repositorio`() = runTest {
        // Arrange
        coEvery { sintomaRepository.actualizarSintoma(sintomaPrueba) } returns Unit

        // Act
        useCase.actualizarSintoma(sintomaPrueba)

        // Assert
        coVerify(exactly = 1) { sintomaRepository.actualizarSintoma(sintomaPrueba) }
    }

    @Test
    fun `eliminarSintoma delega la eliminacion por objeto al repositorio`() = runTest {
        // Arrange
        coEvery { sintomaRepository.eliminarSintoma(sintomaPrueba) } returns Unit

        // Act
        useCase.eliminarSintoma(sintomaPrueba)

        // Assert
        coVerify(exactly = 1) { sintomaRepository.eliminarSintoma(sintomaPrueba) }
    }

    @Test
    fun `eliminarSintomaPorId delega la eliminacion por id al repositorio`() = runTest {
        // Arrange
        coEvery { sintomaRepository.eliminarSintomaPorId(1L) } returns Unit

        // Act
        useCase.eliminarSintomaPorId(1L)

        // Assert
        coVerify(exactly = 1) { sintomaRepository.eliminarSintomaPorId(1L) }
    }

    @Test
    fun `registrarSintoma propaga excepcion sin silenciarla cuando la insercion falla`() = runTest {
        // Arrange
        val errorDb = "Error al insertar síntoma en la base de datos"
        coEvery { sintomaRepository.registrarSintoma(sintomaPrueba) } throws RuntimeException(errorDb)

        // Act
        val resultado = runCatching { useCase.registrarSintoma(sintomaPrueba) }

        // Assert
        assertTrue("La llamada debe resultar en fallo cuando el repositorio lanza excepción", resultado.isFailure)
        assertEquals(errorDb, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { sintomaRepository.registrarSintoma(sintomaPrueba) }
    }
}