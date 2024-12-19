package com.example.viewmodelrm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viewmodelrm.data.TaskDao
import com.example.viewmodelrm.data.GrupoMarcador
import com.example.viewmodelrm.data.ValoracionPlaya
import kotlinx.coroutines.flow.Flow
import com.example.viewmodelrm.data.Valoracion

class MarcadorViewModel(private val taskdao: TaskDao) : ViewModel() {

    val grupoMarcador: Flow<List<GrupoMarcador>> =
        taskdao.getAllgrupoMarcador()

    val valoracionPlaya: Flow<List<ValoracionPlaya>> =
        taskdao.getAllvaloracionPlaya()

    suspend fun insertValoracion(valoracion: Valoracion) {
        taskdao.insertValo(valoracion)
    }

    suspend fun updateValoracion(valoracion: Valoracion) {
        taskdao.updateValoracion(valoracion)
    }

    suspend fun deleteValoracion(valoracion: Valoracion) {
        taskdao.deleteValoracion(valoracion)
    }
}
