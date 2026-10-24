package com.example.demo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.demo.data.local.db.AppDatabase
import com.example.demo.data.local.entidades.PacienteEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var database: AppDatabase

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun database_opensSuccessfully() {
        runBlocking {
            // Comprobar que Room puede abrir o crear la base de datos sin lanzar excepciones
            // Obtenemos un DAO y ejecutamos una consulta simple para forzar la apertura de la BD
            val pacienteDao = database.pacienteDao()
            pacienteDao.obtenerPaciente()

            val isOpen = database.isOpen
            assertEquals(true, isOpen)
            assertNotNull(pacienteDao)
        }
    }

    @Test
    fun insertAndRetrieve_paciente() = runBlocking {
        val pacienteDao = database.pacienteDao()
        val nuevoPaciente = PacienteEntity(
            id = 0,
            nombre = "Juan",
            apellido = "Perez",
            fechaDiagnostico = "2023-01-15"
        )

        val idGenerado = pacienteDao.insertarPaciente(nuevoPaciente)
        
        val pacienteObtenido = pacienteDao.obtenerPacientePorId(idGenerado)
        
        assertNotNull(pacienteObtenido)
        assertEquals(nuevoPaciente.nombre, pacienteObtenido?.nombre)
        assertEquals(nuevoPaciente.apellido, pacienteObtenido?.apellido)
        assertEquals(nuevoPaciente.fechaDiagnostico, pacienteObtenido?.fechaDiagnostico)
    }

    @Test
    fun updateAndDelete_paciente() = runBlocking {
        val pacienteDao = database.pacienteDao()
        val pacienteInicial = PacienteEntity(
            id = 0,
            nombre = "Maria",
            apellido = "Gomez",
            fechaDiagnostico = "2023-05-10"
        )

        // Insertar
        val idGenerado = pacienteDao.insertarPaciente(pacienteInicial)
        val pacienteInsertado = pacienteDao.obtenerPacientePorId(idGenerado)!!

        // Actualizar
        val pacienteModificado = pacienteInsertado.copy(nombre = "Mariana")
        pacienteDao.actualizarPaciente(pacienteModificado)

        // Comprobar actualización
        val pacienteActualizado = pacienteDao.obtenerPacientePorId(idGenerado)
        assertEquals("Mariana", pacienteActualizado?.nombre)

        // Eliminar
        pacienteDao.eliminarPaciente(pacienteActualizado!!)

        // Verificar que ya no aparece
        val pacienteEliminado = pacienteDao.obtenerPacientePorId(idGenerado)
        assertNull(pacienteEliminado)
    }

    @Test
    fun persistentDatabase_retainsDataAfterReopen() { runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dbName = "test_persistence_db"

        // Asegurarnos de limpiar antes de la prueba
        context.deleteDatabase(dbName)

        // Crear base de datos persistente
        var persistentDb = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            dbName
        ).build()

        val paciente = PacienteEntity(
            id = 0,
            nombre = "Carlos",
            apellido = "Lopez",
            fechaDiagnostico = "2024-02-01"
        )

        // Guardar registro
        val id = persistentDb.pacienteDao().insertarPaciente(paciente)

        // Cerrar la instancia de Room
        persistentDb.close()

        // Abrir nuevamente
        persistentDb = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            dbName
        ).build()

        // Comprobar que el registro continúa disponible
        val pacienteRecuperado = persistentDb.pacienteDao().obtenerPacientePorId(id)

        assertNotNull(pacienteRecuperado)
        assertEquals("Carlos", pacienteRecuperado?.nombre)

        // Limpieza tras el test
        persistentDb.close()
        context.deleteDatabase(dbName)
        }
    }
}