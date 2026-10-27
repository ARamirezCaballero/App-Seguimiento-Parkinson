package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.PautaMedicacion
import com.example.demo.dominio.repository.MedicacionRepository
import javax.inject.Inject

class GestionarPautaMedicacionUseCase @Inject constructor(
    private val medicacionRepository: MedicacionRepository
) {

    //Crear pauta para un medicamento
    suspend fun crearPauta(pauta: PautaMedicacion): Long {
        return medicacionRepository.insertarPauta(pauta)
    }

    //Consutlar pauta especifica
    suspend fun obtenerPautaPorId(id: Long): PautaMedicacion? {
        return medicacionRepository.obtenerPautaPorId(id)
    }

    //Consultar todas las pautas de un medicamento
    suspend fun consultarPautas(medicacionId: Long): List<PautaMedicacion> {
        return medicacionRepository.consultarPautas(medicacionId)
    }

    //Consultar pautas vigentes
    suspend fun consultarPautasVigentes(pacienteId: Long, fechaActual: String): List<PautaMedicacion> {
        return medicacionRepository.consultarPautasVigentes(pacienteId, fechaActual)
    }

    //Modificar pauta existente
    suspend fun actualizarPauta(pauta: PautaMedicacion) {
        medicacionRepository.actualizarPauta(pauta)
    }

    //Desactivar pauta sin eliminar historial de tomas
    suspend fun desactivarPauta(pautaId: Long) {
        medicacionRepository.finalizarPauta(pautaId)
    }
}