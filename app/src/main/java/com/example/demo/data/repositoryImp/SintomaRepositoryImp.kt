package com.example.demo.data.repositoryImp

import com.example.demo.data.local.entidades.RegistroSintomaEntity

interface SintomaRepositoryImp {

    //Guardar un nuevo registro de síntoma

    suspend fun guardarSintoma(sintoma: RegistroSintomaEntity)

    //Recuperar un registrp específico

    suspend fun obtenerSintoma(id: Long): RegistroSintomaEntity?

    //Consultar los síntomas registrados durante un periodo

    suspend fun obtenerSintomasPorIntervalo(inicio: Long, fin: Long): List<RegistroSintomaEntity>

    //Modificar un registro existente

    suspend fun modificarSintoma(sintoma: RegistroSintomaEntity)

}