package com.example.demo.data.local.db
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.demo.data.local.db.daos.MedicacionDao
import com.example.demo.data.local.db.daos.PacienteDao
import com.example.demo.data.local.db.daos.RegistroActividadDao
import com.example.demo.data.local.db.daos.RegistroEstadoMotorDao
import com.example.demo.data.local.db.daos.RegistroSintomaDao
import com.example.demo.data.local.db.daos.RegistroSuenoDao
import com.example.demo.data.local.db.daos.CardiacaDao
import com.example.demo.data.local.db.daos.PautaMedicacionDao
import com.example.demo.data.local.db.daos.RegistroMedicacionDao
import com.example.demo.data.local.entidades.MedicacionEntity
import com.example.demo.data.local.entidades.PacienteEntity
import com.example.demo.data.local.entidades.PautaMedicacionEntity
import com.example.demo.data.local.entidades.RegistroActividadEntity
import com.example.demo.data.local.entidades.RegistroEstadoMotorEntity
import com.example.demo.data.local.entidades.RegistroMedicacionEntity
import com.example.demo.data.local.entidades.RegistroSintomaEntity
import com.example.demo.data.local.entidades.RegistroSuenoEntity
import com.example.demo.data.local.entidades.FrecuenciaCardiacaEntity

//Clase para la definición de la base de datos
//Punto de entrada para acceder a los DAOs
//Extiende a RoomDatabase
//Esta clase es usada en el módulo de inyección de dependencias
@Database(

    entities = [
        FrecuenciaCardiacaEntity::class,
        PacienteEntity::class,
        MedicacionEntity::class,
        PautaMedicacionEntity::class,
        RegistroMedicacionEntity::class,
        RegistroSintomaEntity::class,
        RegistroEstadoMotorEntity::class,
        RegistroSuenoEntity::class,
        RegistroActividadEntity::class
    ],
    version = 1,
    exportSchema = true
)

//Tiene TypeConvertes porque algunas entidades tienen enums
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pacienteDao(): PacienteDao
    abstract fun medicacionDao(): MedicacionDao
    abstract fun registroSintomaDao(): RegistroSintomaDao
    abstract fun registroEstadoMotorDao(): RegistroEstadoMotorDao
    abstract fun registroSuenoDao(): RegistroSuenoDao
    abstract fun registroActividadDao(): RegistroActividadDao
    abstract fun registroMedicacionDao(): RegistroMedicacionDao
    abstract fun pautaMedicacionDao(): PautaMedicacionDao
    abstract fun cardiacaDao(): CardiacaDao
    companion object {
        const val NAME = "parkinson_database.db"
    }
}