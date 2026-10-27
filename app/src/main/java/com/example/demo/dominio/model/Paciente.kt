package com.example.demo.dominio.model

import java.time.LocalDate

data class Paciente(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val fechaDiagnostico: LocalDate //se cambio a LocalDate para manejar fechas correctamente, se unificara con entidad mediante mapper
) {

}