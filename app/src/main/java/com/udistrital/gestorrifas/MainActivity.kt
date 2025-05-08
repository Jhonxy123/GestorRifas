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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.udistrital.gestorrifas.ui.theme.GestorRifasTheme
import com.udistrital.gestorrifas.vistas.rifas.TalonarioScreen
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

            var isMenuScreen by remember { mutableStateOf(false) }
            var numVista by remember {mutableStateOf(0)}


            GestorRifasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when(numVista){
                        0 ->{
                            MenuScreen(
                                NuevaRifa = { numVista = 1 }
                            )
                        }
                        1->{
                            RifasScreen(
                                onGuardar = { nombre, fecha ->
                                    rifaViewModel.guardarRifa(nombre, fecha)
                                    numVista = 0
                                },
                                Menu = {
                                    numVista = 0
                                }
                            )
                        }

                    }
                }
                /*
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(isMenuScreen){
                        RifasScreen(
                            onGuardar = { nombre, fecha ->
                                rifaViewModel.guardarRifa(nombre, fecha)
                                isMenuScreen = false
                            },
                            Menu = {
                                isMenuScreen = false
                            }
                        )
                    }else{
                        MenuScreen(
                            NuevaRifa = { isMenuScreen = true }
                        )
                    }
                }
                */
            }
        }
    }
}
