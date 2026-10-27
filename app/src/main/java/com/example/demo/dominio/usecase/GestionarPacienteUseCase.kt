package com.example.demo.dominio.usecase

import com.example.demo.dominio.model.Paciente
import com.example.demo.dominio.repository.PacienteRepository
import javax.inject.Inject


class GestionarPacienteUseCase @Inject constructor(
    private val pacienteRepository: PacienteRepository
    ) {

    suspend fun obtenerPaciente(): Paciente? {
        return pacienteRepository.obtenerPaciente()
    }

    suspend fun insertarPaciente(paciente: Paciente) {
        pacienteRepository.insertarPaciente(paciente)
    }

    suspend fun actualizarPaciente(paciente: Paciente) {
        pacienteRepository.actualizarPaciente(paciente)
    }
}