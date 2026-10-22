package com.example.demo.data.local.db

import androidx.room.TypeConverter
import com.example.demo.data.local.entidades.RegistroEstadoMotorEntity
import com.example.demo.data.local.entidades.RegistroMedicacionEntity
import com.example.demo.data.local.entidades.RegistroSintomaEntity

class Converters {

    @TypeConverter
    fun fromEstadoMotor(value: RegistroEstadoMotorEntity.EstadoMotor): String {
        return value.name
    }

    @TypeConverter
    fun toEstadoMotor(value: String): RegistroEstadoMotorEntity.EstadoMotor {
        return RegistroEstadoMotorEntity.EstadoMotor.valueOf(value)
    }

    @TypeConverter
    fun fromEstadoMedicacion(value: RegistroMedicacionEntity.EstadoMedicacion): String {
        return value.name
    }

    @TypeConverter
    fun toEstadoMedicacion(value: String): RegistroMedicacionEntity.EstadoMedicacion {
        return RegistroMedicacionEntity.EstadoMedicacion.valueOf(value)
    }

    @TypeConverter
    fun fromTipoSintoma(value: RegistroSintomaEntity.TipoSintoma): String {
        return value.name
    }

    @TypeConverter
    fun toTipoSintoma(value: String): RegistroSintomaEntity.TipoSintoma {
        return RegistroSintomaEntity.TipoSintoma.valueOf(value)
    }

}