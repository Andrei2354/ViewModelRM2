package com.example.viewmodelrm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Database(entities = [Marcador::class, Grupo::class, Valoracion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        @OptIn(DelicateCoroutinesApi::class)

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance

                GlobalScope.launch {
                    CargarDatos(instance.taskDao())
                }
                instance
            }
        }

        private suspend fun CargarDatos(taskDao: TaskDao) {
            val gruposExistentes = taskDao.getAllGrupos()
            val marcadoresExistentes = taskDao.getAllMarcadores()
            val comentarios = taskDao.getAllValoracion2()

            if (comentarios.isEmpty()){
                taskDao.insertValo(
                    Valoracion(
                        idPlaya = 1,
                        autor = "Restaurante",
                        descripcion = "dsfsefsefsefsefsefs"),

                )
            }
            if (gruposExistentes.isEmpty()){
                taskDao.insertGrupo(
                    Grupo(typeGrupo = "Restaurante"),
                    Grupo(typeGrupo = "Playa"),
                    Grupo(typeGrupo = "Acuario"),
                    Grupo(typeGrupo = "Parque")
                )
            }
            if (marcadoresExistentes.isEmpty()){
                taskDao.insertMarcador(
                    Marcador(
                        titulo = "Playa del Jabliyo",
                        coordenadaX = 28.99302768657386,
                        coordenadaY =  -13.489908744612295,
                        grupoid = 2
                    ),
                    Marcador(
                        titulo = "Playa Cucharas",
                        coordenadaX = 28.99862964321656,
                        coordenadaY =  -13.48812776095318,
                        grupoid = 2
                    ),
                    Marcador(
                        titulo = "Playa Bastián",
                        coordenadaX = 28.992986562609968,
                        coordenadaY = -13.495383991854991,
                        grupoid = 2
                    ),
                    Marcador(
                        titulo = "Playa de los Charcos",
                        coordenadaX = 29.001728347647582,
                        coordenadaY = -13.48222202006685,
                        grupoid = 2
                    )
                )
            }

        }
    }
}