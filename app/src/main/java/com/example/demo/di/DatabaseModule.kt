package com.example.demo.di

import android.content.Context
import androidx.room.Room
import com.example.demo.data.KeyStoreManager
import com.example.demo.data.local.db.AppDatabase
import com.example.demo.data.local.db.daos.CardiacaDao
import com.example.demo.data.local.db.daos.MedicacionDao
import com.example.demo.data.local.db.daos.PacienteDao
import com.example.demo.data.local.db.daos.PautaMedicacionDao
import com.example.demo.data.local.db.daos.RegistroActividadDao
import com.example.demo.data.local.db.daos.RegistroEstadoMotorDao
import com.example.demo.data.local.db.daos.RegistroMedicacionDao
import com.example.demo.data.local.db.daos.RegistroSintomaDao
import com.example.demo.data.local.db.daos.RegistroSuenoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.zetetic.database.sqlcipher.SQLiteDatabase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import javax.inject.Singleton

//Clase para la inyección de dependencias de la base de datos

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //Proveer a la base de datos
    //Proveer instancia de AppDatabase
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        keyStoreManager: KeyStoreManager
    ): AppDatabase {

        val passphrase = keyStoreManager.getOrCreateDatabasePassphrase()
        val factory = SupportOpenHelperFactory(passphrase, null, true)

        //Configuración y construcción de la base de datos
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.NAME
        )
            .openHelperFactory(factory)
            .build()
    }

    //Proveer instancias de DAOs
    @Provides
    fun providePacienteDao(database: AppDatabase): PacienteDao = database.pacienteDao()

    @Provides
    fun provideMedicacionDao(database: AppDatabase): MedicacionDao = database.medicacionDao()

    @Provides
    fun provideRegistroSintomaDao(database: AppDatabase): RegistroSintomaDao =
        database.registroSintomaDao()

    @Provides
    fun provideRegistroEstadoMotorDao(database: AppDatabase): RegistroEstadoMotorDao =
        database.registroEstadoMotorDao()

    @Provides
    fun provideRegistroSuenoDao(database: AppDatabase): RegistroSuenoDao =
        database.registroSuenoDao()

    @Provides
    fun provideRegistroActividadDao(database: AppDatabase): RegistroActividadDao =
        database.registroActividadDao()

    @Provides
    fun provideRegistroMedicacionDao(database: AppDatabase): RegistroMedicacionDao =
        database.registroMedicacionDao()

    @Provides
    fun providePautaMedicacionDao(database: AppDatabase): PautaMedicacionDao =
        database.pautaMedicacionDao()

    @Provides
    fun provideCardiacaDao(database: AppDatabase): CardiacaDao =
        database.cardiacaDao()
}