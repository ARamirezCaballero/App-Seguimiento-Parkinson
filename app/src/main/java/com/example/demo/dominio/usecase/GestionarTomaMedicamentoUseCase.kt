package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.RegistroMedicacion
import com.example.demo.dominio.repository.RegistroMedicacionRepository
import javax.inject.Inject

class GestionarTomaMedicamentoUseCase @Inject constructor(
    private val registroMedicacionRepository: RegistroMedicacionRepository
) {

    //Crear un registro de tomas
    suspend fun registrarToma(toma: RegistroMedicacion): Long {
        return registroMedicacionRepository.registrarToma(toma)
    }

    //Consultar toma especifica
    suspend fun obtenerTomaPorId(id: Long): RegistroMedicacion? {
        return registroMedicacionRepository.obtenerTomaPorId(id)
    }

    //Consultar tomas en un periodo especifico
    suspend fun consultarTomasPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroMedicacion> {
        return registroMedicacionRepository.consultarTomasPorPeriodo(pacienteId, fechaInicio, fechaFin)
    }

    //Consultar tomas asociadas a una pauta
    suspend fun consultarTomasPorPauta(pautaId: Long): List<RegistroMedicacion> {
        return registroMedicacionRepository.consultarTomasPorPauta(pautaId)
    }

    //Modificar toma existente
    suspend fun actualizarToma(toma: RegistroMedicacion) {
        registroMedicacionRepository.actualizarToma(toma)
    }
}