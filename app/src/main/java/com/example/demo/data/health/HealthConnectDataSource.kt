package com.example.demo.data.health

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.example.demo.data.local.entidades.FrecuenciaCardiacaEntity
import com.example.demo.data.local.entidades.RegistroActividadEntity
import com.example.demo.data.local.entidades.RegistroSuenoEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

data class ResultadoSincronizacionHealth(
    val actividades: List<RegistroActividadEntity>,
    val sesionesSueno: List<RegistroSuenoEntity>,
    val frecuenciasCardiacas: List<FrecuenciaCardiacaEntity>
)

class HealthConnectDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val healthConnectClient: HealthConnectClient?
) {

    suspend fun sincronizarDatos(pacienteId: Long, inicio: Long, fin: Long): ResultadoSincronizacionHealth {
        if (healthConnectClient == null) {
            return ResultadoSincronizacionHealth(emptyList(), emptyList(), emptyList())
        }

        val timeRangeFilter = TimeRangeFilter.between(
            Instant.ofEpochMilli(inicio),
            Instant.ofEpochMilli(fin)
        )

        // 1. Lectura de Actividad (Pasos y Distancia)
        val actividades = try {
            val stepsResponse = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = StepsRecord::class,
                    timeRangeFilter = timeRangeFilter
                )
            )
            val distanceResponse = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = DistanceRecord::class,
                    timeRangeFilter = timeRangeFilter
                )
            )

            val totalDistancia = distanceResponse.records.sumOf { it.distance.inMeters }

            stepsResponse.records.map { record ->
                RegistroActividadEntity(
                    pacienteId = pacienteId,
                    inicio = record.startTime.toEpochMilli(),
                    fin = record.endTime.toEpochMilli(),
                    pasos = record.count.toInt(),
                    distanciaMetros = totalDistancia,
                    fechaHora = record.startTime.toEpochMilli(),
                    fuente = "Health Connect"
                )
            }
        } catch (_: Exception) {
            emptyList()
        }

        // 2. Lectura de Sesiones de Sueño
        val sesionesSueno = try {
            val sleepResponse = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = SleepSessionRecord::class,
                    timeRangeFilter = timeRangeFilter
                )
            )

            sleepResponse.records.map { session ->
                val duracionMin = Duration.between(session.startTime, session.endTime).toMinutes().toDouble()
                var minLigeros = 0
                var minProfundos = 0
                var minREM = 0
                var minDespierto = 0

                session.stages.forEach { stage ->
                    val mins = Duration.between(stage.startTime, stage.endTime).toMinutes().toInt()
                    when (stage.stage) {
                        SleepSessionRecord.STAGE_TYPE_LIGHT -> minLigeros += mins
                        SleepSessionRecord.STAGE_TYPE_DEEP -> minProfundos += mins
                        SleepSessionRecord.STAGE_TYPE_REM -> minREM += mins
                        SleepSessionRecord.STAGE_TYPE_AWAKE -> minDespierto += mins
                    }
                }

                RegistroSuenoEntity(
                    pacienteId = pacienteId,
                    inicio = session.startTime.atZone(ZoneId.systemDefault()).hour,
                    fin = session.endTime.atZone(ZoneId.systemDefault()).hour,
                    duracionMinutos = duracionMin,
                    minutosLigeros = minLigeros,
                    minutosProfundos = minProfundos,
                    minutosREM = minREM,
                    minutosDespierto = minDespierto,
                    fechaHora = session.startTime.toEpochMilli(),
                    fuente = "Health Connect"
                )
            }
        } catch (_: Exception) {
            emptyList()
        }

        // 3. Lectura de Frecuencia Cardíaca
        val frecuencias = try {
            val hrResponse = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = HeartRateRecord::class,
                    timeRangeFilter = timeRangeFilter
                )
            )

            hrResponse.records.flatMap { record ->
                record.samples.map { sample ->
                    FrecuenciaCardiacaEntity(
                        pacienteId = pacienteId,
                        valor = sample.beatsPerMinute.toInt(),
                        fechaHora = sample.time.toEpochMilli(),
                        fuente = "Health Connect"
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }

        return ResultadoSincronizacionHealth(
            actividades = actividades,
            sesionesSueno = sesionesSueno,
            frecuenciasCardiacas = frecuencias
        )
    }
}