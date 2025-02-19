package com.example.viewmodelrm.view

import androidx.compose.runtime.Composable
import com.example.viewmodelrm.viewmodel.MarcadorViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
fun SegundaPantalla(viewModel: MarcadorViewModel, onChangePantalla: () -> Unit) {
    val scope = rememberCoroutineScope()

    var selectedPlayaId by rememberSaveable { mutableStateOf<Int?>(null) }
    val valoraciones by viewModel.valoracionPlaya.collectAsState(initial = emptyList())
    var newAutor by rememberSaveable { mutableStateOf("") }
    var newDescripcion by rememberSaveable { mutableStateOf("") }
    var editValoracion: Valoracion? by remember { mutableStateOf(null) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val grupoMarcador by viewModel.grupoMarcador.collectAsState(initial = emptyList())
    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box {
            Button(onClick = { expanded = true }) {
                Text(text = selectedPlayaId?.toString() ?: "Seleccionar playa")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                grupoMarcador.forEach { playa ->
                    DropdownMenuItem(
                        text = { Text(text = "${playa.marcador.titulo}") },
                        onClick = {
                            selectedPlayaId = playa.marcador.id
                            expanded = false
                        }
                    )
                }
            }
        }
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

        Button(
            onClick = {
                if (newAutor.isNotBlank() && newDescripcion.isNotBlank() && selectedPlayaId != null) {
                    isLoading = true
                    errorMessage = null
                    scope.launch {
                        try {
                            viewModel.insertValoracion(
                                Valoracion(autor = newAutor, descripcion = newDescripcion, idPlaya = selectedPlayaId!!)
                            )
                            newAutor = ""
                            newDescripcion = ""
                        } catch (e: Exception) {
                            errorMessage = "Error al agregar la valoración: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                } else {
                    errorMessage = "Por favor, complete todos los campos y seleccione una playa."
                }
            },
            modifier = Modifier.padding(top = 16.dp),
            enabled = !isLoading
        ) {
            Text("Agregar Valoración")
        }

        Text("Valoraciones de la Playa", fontSize = 24.sp, modifier = Modifier.padding(top = 16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(valoraciones) { valoracion ->
                valoracion.valoracionPlaya.forEach { valo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = { editValoracion = valo }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Autor: ${valo.autor}")
                            Text("Descripción: ${valo.descripcion}")
                            Text("Descripción: ${valo.idPlaya}")
                        }
                    }
                }
            }
        }

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        if (editValoracion != null) {
            AlertDialog(
                onDismissRequest = { editValoracion = null },
                title = { Text("Editar / Eliminar Valoración") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = editValoracion?.autor ?: "",
                            onValueChange = { editValoracion = editValoracion?.copy(autor = it) },
                            label = { Text("Autor") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editValoracion?.descripcion ?: "",
                            onValueChange = { editValoracion = editValoracion?.copy(descripcion = it) },
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