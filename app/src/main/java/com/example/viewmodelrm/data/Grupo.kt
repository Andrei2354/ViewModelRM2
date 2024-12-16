package com.example.viewmodelrm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types")
data class Grupo(
    @PrimaryKey(autoGenerate = true)
    val idGrupo: Int = 0,
    val typeGrupo: String,
    val isCompleted: Boolean = false
)