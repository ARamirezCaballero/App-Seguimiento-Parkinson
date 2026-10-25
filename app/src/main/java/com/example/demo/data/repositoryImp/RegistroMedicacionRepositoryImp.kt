package com.example.demo.data.repositoryImp

import com.example.demo.data.local.entidades.RegistroMedicacionEntity

interface RegistroMedicacionRepositoryImp {

    //Guardar el registro de una toma

    suspend fun guardarTomaMedicacion(toma: RegistroMedicacionEntity)

    //Recuperar una toma específica

    suspend fun obtenerTomaMedicacion(id: Long): RegistroMedicacionEntity?

    //Recuperar las tomas de un intervalo temporal

    suspend fun obtenerTomasMedicacionPorIntervalo(inicio: Long, fin: Long): List<RegistroMedicacionEntity>

    //Modificar un registro existenre

    suspend fun modificarTomaMedicacion(toma: RegistroMedicacionEntity)

}