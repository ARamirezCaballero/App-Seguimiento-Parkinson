package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.Medicacion
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

class GestionarMedicacionUseCaseTest {

    private val medicacionRepository: MedicacionRepository = mockk()
    private lateinit var useCase: GestionarMedicacionUseCase

    private val medicacionPrueba = Medicacion(
        id = 1L,
        pacienteId = 100L,
        principioActivo = "Levodopa",
        nombreComercial = "Lebocar",
        dosis = "100mg",
        unidadDosis = "mg",
        formaFarmaceutica = "Comprimido",
        fechaInicio = LocalDate.of(2025, 1, 1),
        fechaFin = LocalDate.of(2025, 12, 31)
    )

    @Before
    fun setUp() {
        useCase = GestionarMedicacionUseCase(medicacionRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(medicacionRepository)
    }

    @Test
    fun `registrarMedicacion inserta el medicamento y devuelve el id generado`() = runTest {
        // Arrange
        coEvery { medicacionRepository.insertarMedicacion(medicacionPrueba) } returns 10L

        // Act
        val idGenerado = useCase.registrarMedicacion(medicacionPrueba)

        // Assert
        assertEquals(
            "El ID generado por la inserción debe coincidir con el valor retornado por el repositorio",
            10L,
            idGenerado
        )
        coVerify(exactly = 1) { medicacionRepository.insertarMedicacion(medicacionPrueba) }
    }

    @Test
    fun `obtenerMedicamentoPorId devuelve la medicacion cuando existe en el repositorio`() = runTest {
        // Arrange
        coEvery { medicacionRepository.obtenerMedicacionPorId(1L) } returns medicacionPrueba

        // Act
        val resultado = useCase.obtenerMedicamentoPorId(1L)

        // Assert
        assertEquals(
            "El medicamento obtenido debe ser exactamente el simulado por el repositorio",
            medicacionPrueba,
            resultado
        )
        coVerify(exactly = 1) { medicacionRepository.obtenerMedicacionPorId(1L) }
    }

    @Test
    fun `obtenerMedicamentoPorId devuelve null cuando no existe el id consultado`() = runTest {
        // Arrange
        coEvery { medicacionRepository.obtenerMedicacionPorId(99L) } returns null

        // Act
        val resultado = useCase.obtenerMedicamentoPorId(99L)

        // Assert
        assertNull(
            "Se esperaba null cuando el ID del medicamento no existe en el repositorio",
            resultado
        )
        coVerify(exactly = 1) { medicacionRepository.obtenerMedicacionPorId(99L) }
    }

    @Test
    fun `consultarMedicamentos devuelve la lista completa de medicamentos registrados del paciente`() = runTest {
        // Arrange
        val listaMedicaciones = listOf(medicacionPrueba)
        coEvery { medicacionRepository.consultarMedicamentos(100L) } returns listaMedicaciones

        // Act
        val resultado = useCase.consultarMedicamentos(100L)

        // Assert
        assertEquals("La lista de medicamentos debe contener exactamente un elemento", 1, resultado.size)
        assertEquals(medicacionPrueba, resultado.first())
        coVerify(exactly = 1) { medicacionRepository.consultarMedicamentos(100L) }
    }

    @Test
    fun `consultarMedicamentosActivos devuelve los medicamentos vigentes para la fecha`() = runTest {
        // Arrange
        val fechaActual = "2025-03-30"
        val listaActivas = listOf(medicacionPrueba)
        coEvery { medicacionRepository.consultarMedicamentosActivos(100L, fechaActual) } returns listaActivas

        // Act
        val resultado = useCase.consultarMedicamentosActivos(100L, fechaActual)

        // Assert
        assertEquals("La lista de medicamentos activos debe coincidir con la retornada", listaActivas, resultado)
        coVerify(exactly = 1) { medicacionRepository.consultarMedicamentosActivos(100L, fechaActual) }
    }

    @Test
    fun `actualizarMedicacion delega de forma estricta la operacion al repositorio`() = runTest {
        // Arrange
        coEvery { medicacionRepository.actualizarMedicacion(medicacionPrueba) } returns Unit

        // Act
        useCase.actualizarMedicacion(medicacionPrueba)

        // Assert
        coVerify(exactly = 1) { medicacionRepository.actualizarMedicacion(medicacionPrueba) }
    }

    @Test
    fun `registrarMedicacion propaga excepcion sin silenciarla cuando la insercion falla`() = runTest {
        // Arrange
        val errorDb = "Error de escritura en la base de datos"
        coEvery { medicacionRepository.insertarMedicacion(medicacionPrueba) } throws RuntimeException(errorDb)

        // Act
        val resultado = runCatching { useCase.registrarMedicacion(medicacionPrueba) }

        // Assert
        assertTrue("La inserción debe fallar en caso de excepción del repositorio", resultado.isFailure)
        assertEquals(errorDb, resultado.exceptionOrNull()?.message)
        coVerify(exactly = 1) { medicacionRepository.insertarMedicacion(medicacionPrueba) }
    }
}