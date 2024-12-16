package com.example.viewmodelrm.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.viewmodelrm.viewmodel.MarcadorViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodelrm.data.Valoracion
import kotlinx.coroutines.launch

@Composable
fun segundaPantalla(navController: NavHostController, viewModel: MarcadorViewModel) {
    // Cargar las valoraciones desde el ViewModel
    val valoraciones by viewModel.valoracionPlaya.collectAsState(initial = emptyList())

    var newAutor by remember { mutableStateOf("") }
    var newDescripcion by remember { mutableStateOf("") }
    var editValoracion: Valoracion? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Valoraciones de la Playa", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(valoraciones) { valoracion ->
                valoracion.valoracionPlaya.forEach{ elementovalo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = {
                            editValoracion = elementovalo
                        }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Autor: ${elementovalo.autor}")
                            Text("Descripción: ${elementovalo.descripcion}")
                        }
                    }
                }

            }
        }

        // Campos para añadir o editar una valoración
        OutlinedTextField(
            value = newAutor,
            onValueChange = { newAutor = it },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = newDescripcion,
            onValueChange = { newDescripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        // Botón para agregar una nueva valoración
        Button(
            onClick = {
                if (newAutor.isNotBlank() && newDescripcion.isNotBlank()) {
                    scope.launch {
                        viewModel.insertValoracion(
                            Valoracion(autor = newAutor, descripcion = newDescripcion)
                        )
                        newAutor = ""
                        newDescripcion = ""
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp).align(Alignment.End)
        ) {
            Text("Agregar")
        }

        // Diálogo para editar o eliminar una valoración seleccionada
        if (editValoracion != null) {
            AlertDialog(
                onDismissRequest = { editValoracion = null },
                title = { Text("Editar / Eliminar Valoración") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = editValoracion?.autor ?: "",
                            onValueChange = {
                                editValoracion = editValoracion?.copy(autor = it)
                            },
                            label = { Text("Autor") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editValoracion?.descripcion ?: "",
                            onValueChange = {
                                editValoracion = editValoracion?.copy(descripcion = it)
                            },
                            label = { Text("Descripción") },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        scope.launch {
                            editValoracion?.let { viewModel.updateValoracion(it) }
                            editValoracion = null
                        }
                    }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        scope.launch {
                            editValoracion?.let { viewModel.deleteValoracion(it) }
                            editValoracion = null
                        }
                    }) {
                        Text("Eliminar")
                    }
                }
            )
        }
    }
}

