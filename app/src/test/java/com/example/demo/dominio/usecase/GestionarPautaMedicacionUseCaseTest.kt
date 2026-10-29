package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.PautaMedicacion
import com.example.demo.dominio.repository.MedicacionRepository
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

class GestionarPautaMedicacionUseCaseTest {

    private val medicacionRepository: MedicacionRepository = mockk()
    private lateinit var useCase: GestionarPautaMedicacionUseCase

    private val pautaPrueba = PautaMedicacion(
        id = 1L,
        pacienteId = 100L,
        medicacionId = 10L,
        horaInicial = 8,
        intervaloHoras = 8,
        cantidad = 1.0,
        fechaInicio = LocalDate.of(2025, 1, 1),
        fechaFin = LocalDate.of(2025, 12, 31),
        activa = true
    )

    @Before
    fun setUp() {
        useCase = GestionarPautaMedicacionUseCase(medicacionRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(medicacionRepository)
    }

    @Test
    fun `crearPauta inserta la pauta y devuelve el id generado`() = runTest {
        // Arrange
        coEvery { medicacionRepository.insertarPauta(pautaPrueba) } returns 5L

        // Act
        val idGenerado = useCase.crearPauta(pautaPrueba)

        // Assert
        assertEquals(
            "El ID de pauta generado debe coincidir con el devuelto por el repositorio",
            5L,
            idGenerado
        )
        coVerify(exactly = 1) { medicacionRepository.insertarPauta(pautaPrueba) }
    }

    @Test
    fun `obtenerPautaPorId devuelve la pauta cuando existe en el repositorio`() = runTest {
        // Arrange
        coEvery { medicacionRepository.obtenerPautaPorId(1L) } returns pautaPrueba

        // Act
        val resultado = useCase.obtenerPautaPorId(1L)

        // Assert
        assertEquals("La pauta obtenida debe coincidir exactamente con la del repositorio", pautaPrueba, resultado)
        coVerify(exactly = 1) { medicacionRepository.obtenerPautaPorId(1L) }
    }

    @Test
    fun `obtenerPautaPorId devuelve null cuando la pauta no existe`() = runTest {
        // Arrange
        coEvery { medicacionRepository.obtenerPautaPorId(99L) } returns null

        // Act
        val resultado = useCase.obtenerPautaPorId(99L)

        // Assert
        assertNull("Se esperaba null al consultar un id inexistente", resultado)
        coVerify(exactly = 1) { medicacionRepository.obtenerPautaPorId(99L) }
    }

    @Test
    fun `consultarPautas devuelve todas las pautas de un medicamento`() = runTest {
        // Arrange
        val listaPautas = listOf(pautaPrueba)
        coEvery { medicacionRepository.consultarPautas(10L) } returns listaPautas

        // Act
        val resultado = useCase.consultarPautas(10L)

        // Assert
        assertEquals("La lista de pautas debe contener exactamente un elemento", 1, resultado.size)
        assertEquals(pautaPrueba, resultado.first())
        coVerify(exactly = 1) { medicacionRepository.consultarPautas(10L) }
    }

    @Test
    fun `consultarPautasVigentes devuelve unicamente las pautas activas para la fecha`() = runTest {
        // Arrange
        val fecha = "2025-03-30"
        val listaVigentes = listOf(pautaPrueba)
        coEvery { medicacionRepository.consultarPautasVigentes(100L, fecha) } returns listaVigentes

        // Act
        val resultado = useCase.consultarPautasVigentes(100L, fecha)

        // Assert
        assertEquals("Debe retornar la lista de pautas vigentes", listaVigentes, resultado)
        coVerify(exactly = 1) { medicacionRepository.consultarPautasVigentes(100L, fecha) }
    }

    @Test
    fun `actualizarPauta delega la modificacion al repositorio`() = runTest {
        // Arrange
        coEvery { medicacionRepository.actualizarPauta(pautaPrueba) } returns Unit

        // Act
        useCase.actualizarPauta(pautaPrueba)

        // Assert
        coVerify(exactly = 1) { medicacionRepository.actualizarPauta(pautaPrueba) }
    }

    @Test
    fun `desactivarPauta finaliza la pauta sin borrar el historial`() = runTest {
        // Arrange
        coEvery { medicacionRepository.finalizarPauta(1L) } returns Unit

        // Act
        useCase.desactivarPauta(1L)

        // Assert
        coVerify(exactly = 1) { medicacionRepository.finalizarPauta(1L) }
    }

    @Test
    fun `crearPauta propaga excepcion sin silenciarla cuando la insercion falla`() = runTest {
        // Arrange
        val errorDb = "Error de restriccion en la base de datos"
        coEvery { medicacionRepository.insertarPauta(pautaPrueba) } throws RuntimeException(errorDb)

        // Act
        val resultado = runCatching { useCase.crearPauta(pautaPrueba) }

        // Assert
        assertTrue("La llamada debe resultar en fallo cuando el repositorio lanza excepción", resultado.isFailure)
        assertEquals(errorDb, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { medicacionRepository.insertarPauta(pautaPrueba) }
    }
}