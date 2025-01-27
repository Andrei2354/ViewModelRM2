package com.example.viewmodelrm.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.viewmodelrm.viewmodel.MarcadorViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodelrm.data.Valoracion
import kotlinx.coroutines.launch

@Composable
fun segundaPantalla(viewModel: MarcadorViewModel, onChangePantalla: () -> Unit) {
    val scope = rememberCoroutineScope()

    // Observa las valoraciones desde el ViewModel
    val valoraciones by viewModel.valoracionPlaya.collectAsState(initial = emptyList())

    // Estados para nuevos datos
    var newAutor by rememberSaveable { mutableStateOf("") }
    var newDescripcion by rememberSaveable { mutableStateOf("") }

    // Estado para la edición
    var editValoracion: Valoracion? by remember { mutableStateOf(null) }

    // Estado de carga y errores
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Valoraciones de la Playa", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Listado de valoraciones
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(valoraciones) { valoracion ->
                valoracion.valoracionPlaya.forEach { valo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = { editValoracion = valo } // Configura la edición
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Autor: ${valo.autor}")
                            Text("Descripción: ${valo.descripcion}")
                        }
                    }
                }
            }
        }

        // Campos para nueva valoración
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (newAutor.isNotBlank() && newDescripcion.isNotBlank()) {
                        isLoading = true
                        errorMessage = null // Clear any previous errors
                        scope.launch {
                            try {
                                viewModel.insertValoracion(
                                    Valoracion(autor = newAutor, descripcion = newDescripcion)
                                )
                                newAutor = ""
                                newDescripcion = ""
                            } catch (e: Exception) {
                                errorMessage = "Failed to add valoracion: ${e.message}"
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                enabled = !isLoading // Disable the button while loading
            ) {
                Text("Agregar")
            }
        }

        // Error message
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }

        // Diálogo para edición o eliminación
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
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