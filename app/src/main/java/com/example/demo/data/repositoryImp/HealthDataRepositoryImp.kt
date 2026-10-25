package com.example.demo.data.repositoryImp

import com.example.demo.data.local.entidades.FrecuenciaCardiacaEntity
import com.example.demo.data.local.entidades.RegistroActividadEntity
import com.example.demo.data.local.entidades.RegistroSuenoEntity

interface HealthDataRepositoryImp {

    //Sincorinizar datos de Health Connect para un periodo

    suspend fun sincronizarDatosHealthConnect(inicio: Long, fin: Long)

    //Guardar/Actualizar datos de Health Connect

    suspend fun guardarDatosActividadFisica(datos: List<RegistroActividadEntity>)

    suspend fun guardarDatosSueno(datos: List<RegistroSuenoEntity>)

    suspend fun guardarDatosFrecuenciaCardiaca(datos: List<FrecuenciaCardiacaEntity>)


    //Recuperar datos de actividad física

    suspend fun obtenerDatosActividadFisica(inicio: Long, fin: Long): List<RegistroActividadEntity>

    //Recuperar datos de sueño

    suspend fun obtenerDatosSueño(inicio: Long, fin: Long): List<RegistroSuenoEntity>

    //Recuperar datos de frecuencia cardíaca

    suspend fun obtenerDatosFrecuenciaCardiaca(inicio: Long, fin: Long): List<FrecuenciaCardiacaEntity>
}