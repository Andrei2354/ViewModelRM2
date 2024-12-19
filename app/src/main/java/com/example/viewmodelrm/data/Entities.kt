package com.example.viewmodelrm.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

@Entity(tableName = "marcador")
data class Marcador(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val coordenadaY: Double,
    val coordenadaX: Double,
    val grupoid: Int,
    val isCompleted: Boolean = false
) : Serializable

@Entity(tableName = "Valoracion")
data class Valoracion(
    @PrimaryKey(autoGenerate = true)
    val idValo: Int = 0,
    val autor: String = "",
    val descripcion: String = "",
    val isCompleted: Boolean = false
): Serializable


@Entity(tableName = "types")
data class Grupo(
    @PrimaryKey(autoGenerate = true)
    val idGrupo: Int = 0,
    val typeGrupo: String,
    val isCompleted: Boolean = false
)  : Serializable

@Entity
data class ValoracionPlaya(
    @Embedded val marcador: Marcador,
    @Relation(
        parentColumn = "id",
        entityColumn = "idValo"
    )
    val valoracionPlaya: List<Valoracion>
): Serializable


@Entity
data class GrupoMarcador(
    @Embedded val marcador: Marcador,
    @Relation(
        parentColumn = "grupoid",
        entityColumn = "idGrupo"
    )
    val grupoMarcadores: List<Grupo>
): Serializable



