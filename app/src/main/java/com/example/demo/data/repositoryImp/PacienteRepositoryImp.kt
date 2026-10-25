package com.example.demo.data.repositoryImp

import com.example.demo.data.local.entidades.PacienteEntity

interface PacienteRepositoryImp {

    //Obtener Paciente

    suspend fun obtenerPaciente(): PacienteEntity?

    //Guardar Pacient

    suspend fun guardarPaciente(paciente: PacienteEntity)


    //Actualizar Paciente

    suspend fun actualizarPaciente(paciente: PacienteEntity)
}