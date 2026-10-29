package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.FrecuenciaCardiaca
import com.example.demo.dominio.model.RegistroActividad
import com.example.demo.dominio.model.RegistroEstadoMotor
import com.example.demo.dominio.model.RegistroMedicacion
import com.example.demo.dominio.model.RegistroSintoma
import com.example.demo.dominio.model.RegistroSueno
import com.example.demo.dominio.model.enums.EstadoMedicacion
import com.example.demo.dominio.model.enums.EstadoMotor
import com.example.demo.dominio.model.enums.TipoSintoma
import com.example.demo.dominio.repository.HealthDataRepository
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
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class ProcesarIndicadoresUseCaseTest {

    private val registroEstadoMotorRepository: RegistroEstadoMotorRepository = mockk()
    private val sintomaRepository: SintomaRepository = mockk()
    private val registroMedicacionRepository: RegistroMedicacionRepository = mockk()
    private val healthDataRepository: HealthDataRepository = mockk()

    private lateinit var useCase: ProcesarIndicadoresUseCase

    private val fechaBase = LocalDate.of(2025, 3, 30)
    private val fechaHoraBase = LocalDateTime.of(2025, 3, 30, 8, 0)

    @Before
    fun setUp() {
        useCase = ProcesarIndicadoresUseCase(
            registroEstadoMotorRepository,
            sintomaRepository,
            registroMedicacionRepository,
            healthDataRepository
        )
    }

    @After
    fun tearDown() {
        confirmVerified(
            registroEstadoMotorRepository,
            sintomaRepository,
            registroMedicacionRepository,
            healthDataRepository
        )
    }

    @Test
    fun `calcularDistribucionEstadoMotor procesa conteos y porcentajes correctamente`() = runTest {
        // Arrange
        val listaEstados = listOf(
            RegistroEstadoMotor(1, 10, EstadoMotor.ON, fechaBase, "Obs1"),
            RegistroEstadoMotor(2, 10, EstadoMotor.ON, fechaBase, "Obs2"),
            RegistroEstadoMotor(3, 10, EstadoMotor.OFF, fechaBase, "Obs3"),
            RegistroEstadoMotor(4, 10, EstadoMotor.Discinesia, fechaBase, "Obs4")
        )
        coEvery {
            registroEstadoMotorRepository.consultarIntervalosPorPeriodo(10L, 1000L, 2000L)
        } returns listaEstados

        // Act
        val distribucion = useCase.calcularDistribucionEstadoMotor(10L, 1000L, 2000L)

        // Assert
        assertEquals(4, distribucion.totalRegistros)
        assertEquals(50.0, distribucion.porcentajePorEstado[EstadoMotor.ON]!!, 0.01)
        assertEquals(25.0, distribucion.porcentajePorEstado[EstadoMotor.OFF]!!, 0.01)
        assertEquals(25.0, distribucion.porcentajePorEstado[EstadoMotor.Discinesia]!!, 0.01)
        coVerify(exactly = 1) { registroEstadoMotorRepository.consultarIntervalosPorPeriodo(10L, 1000L, 2000L) }
    }

    @Test
    fun `calcularDistribucionEstadoMotor devuelve Sin informacion suficiente cuando no hay registros`() = runTest {
        // Arrange
        coEvery {
            registroEstadoMotorRepository.consultarIntervalosPorPeriodo(10L, 1000L, 2000L)
        } returns emptyList()

        // Act
        val distribucion = useCase.calcularDistribucionEstadoMotor(10L, 1000L, 2000L)

        // Assert
        assertEquals(0, distribucion.totalRegistros)
        assertEquals("Sin información suficiente", distribucion.mensaje)
        coVerify(exactly = 1) { registroEstadoMotorRepository.consultarIntervalosPorPeriodo(10L, 1000L, 2000L) }
    }

    @Test
    fun `obtenerSintomasMotoresPorPeriodo filtra unicamente los sintomas motores`() = runTest {
        // Arrange
        val sintomaMotor = RegistroSintoma(1, 10, TipoSintoma.Temblor, 3, fechaBase)
        val sintomaNoMotor = RegistroSintoma(2, 10, TipoSintoma.Fatiga, 2, fechaBase)
        coEvery {
            sintomaRepository.consultarSintomasPorPeriodo(10L, 1000L, 2000L)
        } returns listOf(sintomaMotor, sintomaNoMotor)

        // Act
        val resultado = useCase.obtenerSintomasMotoresPorPeriodo(10L, 1000L, 2000L)

        // Assert
        assertEquals(1, resultado.size)
        assertEquals(true, resultado.first().esMotor)
        assertEquals("Temblor", resultado.first().tituloSintoma)
        coVerify(exactly = 1) { sintomaRepository.consultarSintomasPorPeriodo(10L, 1000L, 2000L) }
    }

    @Test
    fun `obtenerSintomasNoMotoresPorPeriodo filtra unicamente los sintomas no motores`() = runTest {
        // Arrange
        val sintomaMotor = RegistroSintoma(1, 10, TipoSintoma.Rigidez, 4, fechaBase)
        val sintomaNoMotor = RegistroSintoma(2, 10, TipoSintoma.Fatiga, 3, fechaBase)
        coEvery {
            sintomaRepository.consultarSintomasPorPeriodo(10L, 1000L, 2000L)
        } returns listOf(sintomaMotor, sintomaNoMotor)

        // Act
        val resultado = useCase.obtenerSintomasNoMotoresPorPeriodo(10L, 1000L, 2000L)

        // Assert
        assertEquals(1, resultado.size)
        assertEquals(false, resultado.first().esMotor)
        assertEquals("Fatiga", resultado.first().tituloSintoma)
        coVerify(exactly = 1) { sintomaRepository.consultarSintomasPorPeriodo(10L, 1000L, 2000L) }
    }

    @Test
    fun `calcularAdherenciaMedicacion calcula el porcentaje de tomas realizadas`() = runTest {
        // Arrange
        val tomas = listOf(
            RegistroMedicacion(1, 10, 5, fechaHoraBase, fechaHoraBase, EstadoMedicacion.TOMADA, 1.0),
            RegistroMedicacion(2, 10, 5, fechaHoraBase, fechaHoraBase, EstadoMedicacion.TOMADA, 1.0),
            RegistroMedicacion(3, 10, 5, fechaHoraBase, fechaHoraBase, EstadoMedicacion.TOMADA, 1.0),
            RegistroMedicacion(4, 10, 5, fechaHoraBase, fechaHoraBase, EstadoMedicacion.NO_TOMADA, 1.0)
        )
        coEvery {
            registroMedicacionRepository.consultarTomasPorPeriodo(10L, 1000L, 2000L)
        } returns tomas

        // Act
        val adherencia = useCase.calcularAdherenciaMedicacion(10L, 1000L, 2000L)

        // Assert
        assertEquals(4, adherencia.tomasProgramadas)
        assertEquals(3, adherencia.tomasRealizadas)
        assertEquals(1, adherencia.tomasNoRealizadas)
        assertEquals(75.0, adherencia.porcentajeAdherencia, 0.01)
        coVerify(exactly = 1) { registroMedicacionRepository.consultarTomasPorPeriodo(10L, 1000L, 2000L) }
    }

    @Test
    fun `calcularInterrupcionesSueno diferencia noches con y sin despertares largos`() = runTest {
        // Arrange
        val nocheConInterrupcion = RegistroSueno(1, 10, 23, 7, 480.0, 200, 100, 80, 40, fechaHoraBase, "HC")
        val nocheSinInterrupcion = RegistroSueno(2, 10, 23, 7, 480.0, 240, 120, 90, 15, fechaHoraBase, "HC")
        coEvery {
            healthDataRepository.obtenerDatosSueno(10L, 1000L, 2000L)
        } returns listOf(nocheConInterrupcion, nocheSinInterrupcion)

        // Act
        val stats = useCase.calcularInterrupcionesSueno(10L, 1000L, 2000L)

        // Assert
        assertEquals(2, stats.nochesTotales)
        assertEquals(1, stats.nochesConInterrupcion)
        assertEquals(1, stats.nochesSinInterrupcion)
        assertEquals(50.0, stats.porcentajeConInterrupcion, 0.01)
        assertEquals(50.0, stats.porcentajeSinInterrupcion, 0.01)
        coVerify(exactly = 1) { healthDataRepository.obtenerDatosSueno(10L, 1000L, 2000L) }
    }

    @Test
    fun `obtenerSerieActividadPorPeriodo delega la consulta al repositorio`() = runTest {
        // Arrange
        val actividad = RegistroActividad(1, 10, 1000, 2000, 5000, 3500.0, fechaHoraBase, "HC")
        coEvery {
            healthDataRepository.obtenerDatosActividadFisica(10L, 1000L, 2000L)
        } returns listOf(actividad)

        // Act
        val resultado = useCase.obtenerSerieActividadPorPeriodo(10L, 1000L, 2000L)

        // Assert
        assertEquals(1, resultado.size)
        assertEquals(actividad, resultado.first())
        coVerify(exactly = 1) { healthDataRepository.obtenerDatosActividadFisica(10L, 1000L, 2000L) }
    }

    @Test
    fun `obtenerSerieFrecuenciaCardiacaPorPeriodo delega la consulta al repositorio`() = runTest {
        // Arrange
        val cardiaca = FrecuenciaCardiaca(1, 10, 75, fechaHoraBase, "HC")
        coEvery {
            healthDataRepository.obtenerDatosFrecuenciaCardiaca(10L, 1000L, 2000L)
        } returns listOf(cardiaca)

        // Act
        val resultado = useCase.obtenerSerieFrecuenciaCardiacaPorPeriodo(10L, 1000L, 2000L)

        // Assert
        assertEquals(1, resultado.size)
        assertEquals(cardiaca, resultado.first())
        coVerify(exactly = 1) { healthDataRepository.obtenerDatosFrecuenciaCardiaca(10L, 1000L, 2000L) }
    }
}