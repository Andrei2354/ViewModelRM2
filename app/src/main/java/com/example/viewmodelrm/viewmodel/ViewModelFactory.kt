package com.example.viewmodelrm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelrm.data.TaskDao

class ViewModelFactory(private val taskdao: TaskDao) : ViewModelProvider.Factory {
    override fun <datosEnviar : ViewModel> create(modelClass: Class<datosEnviar>): datosEnviar {
        if (modelClass.isAssignableFrom(MarcadorViewModel::class.java)) {
            return MarcadorViewModel(taskdao) as datosEnviar
        }
        throw IllegalArgumentException("Error en Archivo ViewModelFactory")
    }
}