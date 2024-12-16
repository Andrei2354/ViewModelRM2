package com.example.viewmodelrm.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class ValoracionPlaya(
    @Embedded val marcador: Marcador,
    @Relation(
        parentColumn = "id",
        entityColumn = "idValo"
    )
    val valoracionPlaya: List<Valoracion>
)