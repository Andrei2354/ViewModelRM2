package com.example.viewmodelrm.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.viewmodelrm.viewmodel.MarcadorViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: MarcadorViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "crud",
        modifier = modifier
    ) {
        composable("mapa") {
            Pantallamapa(navController = navController, viewModel = viewModel)
        }
        composable("crud") {
            segundaPantalla(navController = navController, viewModel = viewModel)
        }
    }
}