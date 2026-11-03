package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.ItemHistorial
import com.example.demo.dominio.model.enums.CategoriaHistorial
import com.example.demo.dominio.model.enums.TipoRegistroHistorial
import com.example.demo.dominio.repository.HealthDataRepository
import com.example.demo.dominio.repository.MedicacionRepository
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
import com.example.demo.dominio.repository.RegistroMedicacionRepository
import com.example.demo.dominio.repository.SintomaRepository
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GestionarHistorialUseCase @Inject constructor(
    private val registroMedicacionRepository: RegistroMedicacionRepository,
    private val medicacionRepository: MedicacionRepository,
    private val sintomaRepository: SintomaRepository,
    private val registroEstadoMotorRepository: RegistroEstadoMotorRepository,
    private val healthDataRepository: HealthDataRepository
) {

    suspend fun consultarHistorial(
        pacienteId: Long,
        fechaInicio: LocalDate,
        fechaFin: LocalDate,
        categoria: CategoriaHistorial
    ): List<ItemHistorial> {
        val inicioEpoch = fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val finEpoch = fechaFin.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val items = mutableListOf<ItemHistorial>()

        // 1. Medicamentos (Tomas registradas)
        if (categoria == CategoriaHistorial.TODOS || categoria == CategoriaHistorial.MEDICAMENTOS) {
            val tomas = registroMedicacionRepository.consultarTomasPorPeriodo(pacienteId, inicioEpoch, finEpoch)
            val medicamentos = medicacionRepository.consultarMedicamentos(pacienteId).associateBy { it.id }

            tomas.forEach { toma ->
                val pauta = medicacionRepository.obtenerPautaPorId(toma.pautaId)
                val med = pauta?.let { medicamentos[it.medicacionId] }
                val nombreMed = med?.nombreComercial ?: "Medicamento"
                val dosisStr = med?.dosis ?: ""

                val horaFormateada = toma.fechaHoraProgramada.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                
                items.add(
                    ItemHistorial(
                        id = toma.id,
                        nombre = "$nombreMed $dosisStr".trim() + " - " + toma.estado.name.lowercase().replaceFirstChar { it.uppercase() },
                        detalles = "Hora programada: $horaFormateada hs",
                        categoria = CategoriaHistorial.MEDICAMENTOS,
                        tipoRegistro = TipoRegistroHistorial.TOMA_MEDICACION,
                        fechaHora = toma.fechaHoraReal,
                        origenDatos = "Registro manual",
                        referenciaId = toma.id
                    )
                )
            }
        }

        // 2. Síntomas
        if (categoria == CategoriaHistorial.TODOS || categoria == CategoriaHistorial.SINTOMAS) {
            val sintomas = sintomaRepository.consultarSintomasPorPeriodo(pacienteId, inicioEpoch, finEpoch)
            sintomas.forEach { sintoma ->
                items.add(
                    ItemHistorial(
                        id = sintoma.id,
                        nombre = "${sintoma.tipoSintoma.titulo} (Severidad: ${sintoma.severidad})",
                        detalles = sintoma.tipoSintoma.descripcion,
                        categoria = CategoriaHistorial.SINTOMAS,
                        tipoRegistro = TipoRegistroHistorial.SINTOMA,
                        fechaHora = sintoma.fechaHora,
                        origenDatos = "Registro manual",
                        referenciaId = sintoma.id
                    )
                )
            }
        }

        // 3. Estado Motor
        if (categoria == CategoriaHistorial.TODOS || categoria == CategoriaHistorial.ESTADO_MOTOR) {
            val estados = registroEstadoMotorRepository.consultarIntervalosPorPeriodo(pacienteId, inicioEpoch, finEpoch)
            estados.forEach { estado ->
                items.add(
                    ItemHistorial(
                        id = estado.id,
                        nombre = "Estado Motor: ${estado.estadoMotor.name}",
                        detalles = estado.observacion.ifBlank { "Sin observaciones" },
                        categoria = CategoriaHistorial.ESTADO_MOTOR,
                        tipoRegistro = TipoRegistroHistorial.ESTADO_MOTOR,
                        fechaHora = estado.fechaHora,
                        origenDatos = "Registro manual",
                        referenciaId = estado.id
                    )
                )
            }
        }

        // 4. Smartwatch (Health Connect)
        if (categoria == CategoriaHistorial.TODOS || categoria == CategoriaHistorial.SMARTWATCH) {
            val actividades = healthDataRepository.obtenerDatosActividadFisica(pacienteId, inicioEpoch, finEpoch)
            actividades.forEach { act ->
                items.add(
                    ItemHistorial(
                        id = act.id,
                        nombre = "Actividad Física: ${act.pasos} pasos",
                        detalles = "Distancia: ${act.distanciaMetros.toInt()} metros",
                        categoria = CategoriaHistorial.SMARTWATCH,
                        tipoRegistro = TipoRegistroHistorial.ACTIVIDAD,
                        fechaHora = act.fechaHora,
                        origenDatos = act.fuente,
                        referenciaId = act.id
                    )
                )
            }

            val sueno = healthDataRepository.obtenerDatosSueno(pacienteId, inicioEpoch, finEpoch)
            sueno.forEach { s ->
                val horas = (s.duracionMinutos / 60.0)
                items.add(
                    ItemHistorial(
                        id = s.id,
                        nombre = "Sesión de Sueño: ${String.format("%.1f", horas)} hs",
                        detalles = "Despierto: ${s.minutosDespierto} min, Profundo: ${s.minutosProfundos} min",
                        categoria = CategoriaHistorial.SMARTWATCH,
                        tipoRegistro = TipoRegistroHistorial.SUENO,
                        fechaHora = s.fechaHora,
                        origenDatos = s.fuente,
                        referenciaId = s.id
                    )
                )
            }
            
            val frecCardiaca = healthDataRepository.obtenerDatosFrecuenciaCardiaca(pacienteId, inicioEpoch, finEpoch)
            frecCardiaca.forEach { f ->
                items.add(
                    ItemHistorial(
                        id = f.id,
                        nombre = "Frecuencia Cardíaca: ${f.valor} bpm",
                        detalles = "Capturado por el smartwatch",
                        categoria = CategoriaHistorial.SMARTWATCH,
                        tipoRegistro = TipoRegistroHistorial.ACTIVIDAD, // Se puede cambiar si en el futuro agregas TipoRegistroHistorial.FRECUENCIA_CARDIACA
                        fechaHora = f.fechaHora,
                        origenDatos = f.fuente,
                        referenciaId = f.id
                    )
                )
            }
        }

        return items.sortedByDescending { it.fechaHora }
    }

    suspend fun eliminarRegistro(item: ItemHistorial) {
        when (item.tipoRegistro) {
            TipoRegistroHistorial.TOMA_MEDICACION -> {
                registroMedicacionRepository.eliminarTomaPorId(item.referenciaId)
            }
            TipoRegistroHistorial.SINTOMA -> {
                sintomaRepository.eliminarSintomaPorId(item.referenciaId)
            }
            TipoRegistroHistorial.ESTADO_MOTOR -> {
                registroEstadoMotorRepository.eliminarEstadoMotorPorId(item.referenciaId)
            }
            else -> {
                // Las lecturas pasivas de Health Connect son de lectura
            }
        }
    }

    suspend fun actualizarSeveridadSintoma(id: Long, nuevaSeveridad: Int) {
        val sintoma = sintomaRepository.obtenerSintomaPorId(id) ?: return
        sintomaRepository.actualizarSintoma(sintoma.copy(severidad = nuevaSeveridad))
    }

    suspend fun actualizarHoraToma(id: Long, nuevaHora: Int, nuevoMinuto: Int) {
        val toma = registroMedicacionRepository.obtenerTomaPorId(id) ?: return
        val nuevaFechaHoraReal = toma.fechaHoraReal.withHour(nuevaHora).withMinute(nuevoMinuto)
        registroMedicacionRepository.actualizarToma(toma.copy(fechaHoraReal = nuevaFechaHoraReal))
    }

    suspend fun actualizarHoraEstadoMotor(id: Long, nuevaHora: Int, nuevoMinuto: Int) {
        val estado = registroEstadoMotorRepository.obtenerEstadoMotorPorId(id) ?: return
        val nuevaFechaHora = estado.fechaHora.withHour(nuevaHora).withMinute(nuevoMinuto)
        registroEstadoMotorRepository.actualizarEstadoMotor(estado.copy(fechaHora = nuevaFechaHora))
    }
}
