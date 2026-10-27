package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.Medicacion
import com.example.demo.dominio.repository.MedicacionRepository
import javax.inject.Inject

class GestionarMedicacionUseCase @Inject constructor(
    private val medicacionRepository: MedicacionRepository
) {

    //Registrar un medicamento nuevo
    suspend fun registrarMedicacion(medicacion: Medicacion): Long {
        return medicacionRepository.insertarMedicacion(medicacion)
    }

    //Consultar medicamento especifico
    suspend fun obtenerMedicamentoPorId(id: Long): Medicacion? {
        return medicacionRepository.obtenerMedicacionPorId(id)
    }

    //Obtener medicamentos registrados
    suspend fun consultarMedicamentos(pacienteId: Long): List<Medicacion> {
        return medicacionRepository.consultarMedicamentos(pacienteId)
    }

    //Consultar medicamentos activos
    suspend fun consultarMedicamentosActivos(pacienteId: Long, fechaActual: String): List<Medicacion> {
        return medicacionRepository.consultarMedicamentosActivos(pacienteId, fechaActual)
    }

    //Modificar medicamento
    suspend fun actualizarMedicacion(medicacion: Medicacion) {
        medicacionRepository.actualizarMedicacion(medicacion)
    }
}