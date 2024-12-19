package com.example.viewmodelrm.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.viewmodelrm.data.AppDatabase
import com.example.viewmodelrm.viewmodel.MarcadorViewModel
import com.example.viewmodelrm.viewmodel.ViewModelFactory

@Composable
fun PantallaApp(viewModel: MarcadorViewModel, navController: NavHostController = rememberNavController()) {
    var pantallamapa by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //val destino = if (pantallamapa) "crud" else "mapa"
                    //navController.navigate(destino)
                    pantallamapa = !pantallamapa
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Switch Screen"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        if (pantallamapa) {
            Pantallamapa(viewModel) { pantallamapa = false }
        } else {
            segundaPantalla(viewModel) { pantallamapa = true }
        }
//        NavHost(
//            navController = navController,
//            startDestination = "crud",
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable("mapa") {
//                Pantallamapa(navController = navController, viewModel = viewModel)
//            }
//            composable("crud") {
//                segundaPantalla(navController = navController, viewModel = viewModel)
//            }
//        }
    }
}