package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.RegistroSintoma
import com.example.demo.dominio.repository.SintomaRepository
import javax.inject.Inject

class GestionarSintomaUseCase @Inject constructor(
    private val sintomaRepository: SintomaRepository
) {

    //Registrar sintoma
    suspend fun registrarSintoma(sintoma: RegistroSintoma): Long {
        return sintomaRepository.registrarSintoma(sintoma)
    }

    //Obtener sintoma por id
    suspend fun obtenerSintomaPorId(id: Long): RegistroSintoma? {
        return sintomaRepository.obtenerSintomaPorId(id)
    }

    //Consultar sintoma por periodo
    suspend fun consultarSintomasPorPeriodo(
        pacienteId: Long,
        fechaInicio: Long,
        fechaFin: Long
    ): List<RegistroSintoma> {
        return sintomaRepository.consultarSintomasPorPeriodo(pacienteId, fechaInicio, fechaFin)
    }

    //Actualizar sintoma
    suspend fun actualizarSintoma(sintoma: RegistroSintoma) {
        sintomaRepository.actualizarSintoma(sintoma)
    }

    //Eliminar sintoma
    suspend fun eliminarSintoma(sintoma: RegistroSintoma) {
        sintomaRepository.eliminarSintoma(sintoma)
    }

    suspend fun eliminarSintomaPorId(id: Long) {
        sintomaRepository.eliminarSintomaPorId(id)
    }
}