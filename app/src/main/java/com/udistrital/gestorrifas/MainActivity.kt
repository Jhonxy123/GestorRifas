package com.udistrital.gestorrifas

import MenuScreen
import RifasScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.udistrital.gestorrifas.core.NavigationWrapper
import com.udistrital.gestorrifas.ui.theme.GestorRifasTheme
//import com.udistrital.gestorrifas.vistas.rifas.RifasScreen
import com.udistrital.gestorrifas.vistas.rifas.TalonarioScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                NavigationWrapper()
                //TalonarioScreen()
                //RifasScreen()
                //MenuScreen()
                /*Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamamos a tu pantalla
                    RifasScreen { nombre, fecha ->
                        // Aquí recibes el nombre y la fecha cuando el usuario pulsa "Guardar"
                        // Por ejemplo, muestra un log o navega a otra pantalla
                        println("Rifa creada: $nombre en fecha $fecha")
                    }
                }*/
            }
        }
    }
}


