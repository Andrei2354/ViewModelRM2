package com.example.viewmodelrm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Valoracion")
data class Valoracion(
    @PrimaryKey(autoGenerate = true)
    val idValo: Int = 0,
    val autor: String,
    val descripcion: String,
    val isCompleted: Boolean = false
)