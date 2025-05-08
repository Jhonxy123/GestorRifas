package com.udistrital.gestorrifas

import MenuScreen
import RifasScreen
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.udistrital.gestorrifas.ui.theme.GestorRifasTheme
import com.udistrital.gestorrifas.vistas.viewmodel.RifaViewModel
import com.udistrital.gestorrifas.vistas.viewmodel.RifaViewModelFactory

class MainActivity : ComponentActivity() {

    // Crear el ViewModel usando la fábrica personalizada
    private val rifaViewModel: RifaViewModel by viewModels {
        RifaViewModelFactory(application as Application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorRifasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Pasamos la lógica de guardar desde el ViewModel


                    /*RifasScreen { nombre, fecha ->
                        rifaViewModel.guardarRifa(nombre, fecha)
                    }*/
                    MenuScreen()
                }
            }
        }
    }
}
