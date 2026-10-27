package com.example.demo.dependencias

//Enlaza las interfaces de repositorios con sus implementaciones

import com.example.demo.data.repositoryImp.EstadoMotorRepositoryImpl
import com.example.demo.data.repositoryImp.HealthDataRepositoryImpl
import com.example.demo.data.repositoryImp.MedicacionRepositoryImpl
import com.example.demo.data.repositoryImp.PacienteRepositoryImpl
import com.example.demo.data.repositoryImp.RegistroMedicacionRepositoryImpl
import com.example.demo.data.repositoryImp.SintomaRepositoryImpl
import com.example.demo.dominio.repository.HealthDataRepository
import com.example.demo.dominio.repository.MedicacionRepository
import com.example.demo.dominio.repository.PacienteRepository
import com.example.demo.dominio.repository.RegistroEstadoMotorRepository
import com.example.demo.dominio.repository.RegistroMedicacionRepository
import com.example.demo.dominio.repository.SintomaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPacienteRepository(
        impl: PacienteRepositoryImpl
    ): PacienteRepository

    @Binds
    @Singleton
    abstract fun bindMedicacionRepository(
        impl: MedicacionRepositoryImpl
    ): MedicacionRepository

    @Binds
    @Singleton
    abstract fun bindSintomaRepository(
        impl: SintomaRepositoryImpl
    ): SintomaRepository

    @Binds
    @Singleton
    abstract fun bindRegistroMedicacionRepository(
        impl: RegistroMedicacionRepositoryImpl
    ): RegistroMedicacionRepository

    @Binds
    @Singleton
    abstract fun bindRegistroEstadoMotorRepository(
        impl: EstadoMotorRepositoryImpl
    ): RegistroEstadoMotorRepository

    @Binds
    @Singleton
    abstract fun bindHealthDataRepository(
        impl: HealthDataRepositoryImpl
    ): HealthDataRepository
}