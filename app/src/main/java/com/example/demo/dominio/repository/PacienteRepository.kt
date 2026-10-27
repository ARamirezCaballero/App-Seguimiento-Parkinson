package com.example.demo.dominio.repository

import com.example.demo.dominio.model.Paciente

interface PacienteRepository {
    suspend fun obtenerPaciente(): Paciente?
    suspend fun insertarPaciente(paciente: Paciente)
    suspend fun actualizarPaciente(paciente: Paciente)
}