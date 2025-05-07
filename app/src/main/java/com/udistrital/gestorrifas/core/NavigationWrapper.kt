package com.udistrital.gestorrifas.core

import MenuScreen
import RifasScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Menu){
        composable<Menu>{
            MenuScreen{ navController.navigate(Rifas) }
        }

        composable<Rifas> {
            //RifasScreen(onGuardar = (nombre: String, fecha: LocalDate) -> Unit)
            RifasScreen(onGuardar = { nombre, fecha ->
                // Aquí defines qué hacer cuando se llama a onGuardar
                Log.d("RifasScreen", "Nombre: $nombre, Fecha: $fecha")
                // o navegar a otra pantalla, guardar en ViewModel, etc.
            })
        }
    }
}