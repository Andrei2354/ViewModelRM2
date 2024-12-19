package com.example.viewmodelrm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM marcador")
    fun getAllgrupoMarcador(): Flow<List<GrupoMarcador>>

    @Transaction
    @Query("SELECT * FROM marcador")
    fun getAllvaloracionPlaya(): Flow<List<ValoracionPlaya>>

    @Insert
    suspend fun insertMarcador(vararg marcador: Marcador)

    @Insert
    suspend fun insertGrupo(vararg grupo: Grupo)

    @Insert
    suspend fun insertValo(valoracion: Valoracion)

    @Query("SELECT * FROM types")
    suspend fun getAllGrupos(): List<Grupo>

    @Query("SELECT * FROM marcador")
    suspend fun getAllMarcadores(): List<Marcador>

    @Query("SELECT * FROM valoracion")
    fun getAllValoracion2(): List<Valoracion>

    @Query("SELECT * FROM valoracion")
    fun getAllValoracion(): Flow<List<Valoracion>>
    @Update
    suspend fun updateValoracion(valoracion: Valoracion)

    @Delete
    suspend fun deleteValoracion(valoracion: Valoracion)
}