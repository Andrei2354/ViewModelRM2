package com.example.viewmodelrm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodelrm.data.AppDatabase
import com.example.viewmodelrm.ui.theme.ViewModelRMTheme
import com.example.viewmodelrm.viewmodel.MarcadorViewModel
import com.example.viewmodelrm.viewmodel.ViewModelFactory
import com.example.viewmodelrm.view.PantallaApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val database = AppDatabase.getDatabase(this)
            val taskdao = database.taskDao()
            val viewModel: MarcadorViewModel = viewModel(factory = ViewModelFactory(taskdao))
            ViewModelRMTheme {
                PantallaApp(viewModel = viewModel)
            }
        }
    }
}