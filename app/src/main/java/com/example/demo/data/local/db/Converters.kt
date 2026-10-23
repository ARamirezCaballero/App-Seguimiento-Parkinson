package com.example.demo.data.local.db

import androidx.room.TypeConverter
import com.example.demo.data.local.enums.EstadoMedicacion
import com.example.demo.data.local.enums.EstadoMotor
import com.example.demo.data.local.enums.TipoSintoma

class Converters {

    @TypeConverter
    fun fromEstadoMotor(value: EstadoMotor): String {
        return value.name
    }

    @TypeConverter
    fun toEstadoMotor(value: String): EstadoMotor {
        return EstadoMotor.valueOf(value)
    }

    @TypeConverter
    fun fromEstadoMedicacion(value: EstadoMedicacion): String {
        return value.name
    }

    @TypeConverter
    fun toEstadoMedicacion(value: String): EstadoMedicacion {
        return EstadoMedicacion.valueOf(value)
    }

    @TypeConverter
    fun fromTipoSintoma(value: TipoSintoma): String {
        return value.name
    }

    @TypeConverter
    fun toTipoSintoma(value: String): TipoSintoma {
        return TipoSintoma.valueOf(value)
    }

}