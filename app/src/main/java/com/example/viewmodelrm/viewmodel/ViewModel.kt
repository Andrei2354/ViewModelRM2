package com.example.viewmodelrm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viewmodelrm.data.TaskDao
import com.example.viewmodelrm.data.GrupoMarcador
import com.example.viewmodelrm.data.ValoracionPlaya
import kotlinx.coroutines.flow.Flow

class MarcadorViewModel(private val taskdao: TaskDao) : ViewModel() {

    val grupoMarcador: Flow<List<GrupoMarcador>> =
        taskdao.getAllgrupoMarcador()

    val valoracionPlaya: Flow<List<ValoracionPlaya>> =
        taskdao.getAllvaloracionPlaya()


}