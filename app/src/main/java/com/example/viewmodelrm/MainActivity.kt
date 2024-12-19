package com.example.viewmodelrm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodelrm.data.AppDatabase
import com.example.viewmodelrm.ui.theme.ViewModelRMTheme
import androidx.compose.foundation.layout.padding
import com.example.viewmodelrm.viewmodel.MarcadorViewModel
import com.example.viewmodelrm.viewmodel.ViewModelFactory
import androidx.navigation.compose.rememberNavController
import com.example.viewmodelrm.view.PantallaApp
//import com.example.viewmodelrm.view.MainView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val navController = rememberNavController()
            val database = AppDatabase.getDatabase(this)
            val taskdao = database.taskDao()
            val viewModel: MarcadorViewModel = viewModel(factory = ViewModelFactory(taskdao))
            ViewModelRMTheme {
                PantallaApp(viewModel = viewModel)
            }
        }
    }
}