package com.example.demo.data.repositoryImp

import com.example.demo.data.local.entidades.MedicacionEntity
import com.example.demo.data.local.entidades.PautaMedicacionEntity

interface MedicacionRepositoryImp {

    //Regitrar medicamento

    suspend fun registrarMedicacion(medicacion: MedicacionEntity)

    //Obtener Medicamento especifico

    suspend fun obtenerMedicamento(id: Long): MedicacionEntity?

    //Obtener Medicamentos registrados

    suspend fun obtenerMedicamentos(): List<MedicacionEntity>

    //Modificar datos Medicamento

    suspend fun modificarMedicacion(medicacion: MedicacionEntity)

    // Guardar una Pauta de medicamento

    suspend fun guardarPautaMedicacion(pauta: PautaMedicacionEntity)

    //Consultar Pauta de un medicamento

    suspend fun consultarPautaMedicacion(id: Long): PautaMedicacionEntity?

    //Consultar una pauta específica

    suspend fun consultarPautaEspecifica(pautaId: Long): PautaMedicacionEntity?

    //Modificar una pauta de medicamento

    suspend fun modificarPautaMedicacion(medicacion: PautaMedicacionEntity)

}