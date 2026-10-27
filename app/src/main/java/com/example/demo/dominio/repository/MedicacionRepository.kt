package com.example.demo.dominio.repository

import com.example.demo.dominio.model.Medicacion
import com.example.demo.dominio.model.PautaMedicacion

interface MedicacionRepository {

    suspend fun insertarMedicacion(medicacion: Medicacion): Long

    suspend fun obtenerMedicacionPorId(id: Long): Medicacion?

    suspend fun consultarMedicamentos(pacienteId: Long): List<Medicacion>

    suspend fun consultarMedicamentosActivos(pacienteId: Long, fechaActual: String): List<Medicacion>

    suspend fun actualizarMedicacion(medicacion: Medicacion)

    suspend fun insertarPauta(pauta: PautaMedicacion): Long

    suspend fun obtenerPautaPorId(id: Long): PautaMedicacion?

    suspend fun consultarPautas(medicacionId: Long): List<PautaMedicacion>

    suspend fun consultarPautasVigentes(pacienteId: Long, fechaActual: String): List<PautaMedicacion>

    suspend fun consultarPautasHistoricas(pacienteId: Long): List<PautaMedicacion>

    suspend fun actualizarPauta(pauta: PautaMedicacion)

    suspend fun finalizarPauta(pautaId: Long)
}