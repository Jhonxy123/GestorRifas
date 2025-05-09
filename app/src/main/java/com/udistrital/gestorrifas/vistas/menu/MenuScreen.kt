// Paquete donde se encuentra esta pantalla
package com.udistrital.gestorrifas.vistas.menu

// Importaciones necesarias de Jetpack Compose y otras librerías
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.udistrital.gestorrifas.datos.local.entidad.Rifa
import com.udistrital.gestorrifas.vistas.viewmodel.RifaViewModel

// Función composable que representa la pantalla del menú principal
@Composable
fun MenuScreen(
    viewModel: RifaViewModel = viewModel(),     // ViewModel para acceder a los datos de rifas
    NuevaRifa: () -> Unit,                      // Lambda para crear una nueva rifa
    Talonario: (String) -> Unit                 // Lambda para ver detalles del talonario
) {
    var searchText by remember { mutableStateOf("") }  // Estado local para almacenar el texto de búsqueda

    // 1) Observamos la lista de rifas desde el ViewModel
    val rifasEnt by viewModel.rifas.observeAsState(emptyList())

    // 2) Convertimos las rifas en un modelo de UI para usarlo en la pantalla
    val rifasUI: List<RifaUI> = rifasEnt.map {
        RifaUI(
            nombre = it.nombre,
            inscritos = it.boletas.count { b -> b != 0 },  // Cuenta cuántas boletas tienen valor distinto de 0
            fecha = it.fecha.toString()                    // Convierte la fecha a String
        )
    }

    // Contenedor scaffold (vacío por ahora, se puede usar para Snackbars, TopBar, etc.)
    Scaffold { innerPadding -> }

    // Contenedor principal en columna vertical
    Column(
        modifier = Modifier
            .fillMaxSize()           // Ocupa todo el alto y ancho disponible
            .padding(16.dp)          // Margen de 16dp alrededor
    ) {
        // Título centrado
        Text(
            text = "Rifas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp)) // Espacio vertical

        // Fila que contiene el campo de búsqueda
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,              // Texto actual del campo
                onValueChange = {
                    searchText = it              // Actualiza el estado local
                    viewModel.setQuery(it.trim())// Llama al método del ViewModel para filtrar resultados
                },
                modifier = Modifier
                    .weight(1f)                  // Ocupa el máximo espacio disponible
                    .height(56.dp),              // Altura del campo
                placeholder = { Text("Buscar por nombre o fecha") }, // Texto indicativo
                singleLine = true,               // Una sola línea de texto
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) } // Icono de búsqueda
            )
        }

        Spacer(Modifier.height(24.dp)) // Espacio entre búsqueda y encabezados

        // Encabezado de las columnas
        Row(Modifier.fillMaxWidth()) {
            Text("Nombre", Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("Inscritos", Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("Fecha Rifa", Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Divider(thickness = 1.dp) // Línea divisoria

        // 3) Lista de rifas usando LazyColumn (scrollable y eficiente)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)         // Ocupa el espacio vertical restante
                .padding(top = 8.dp)
        ) {
            // Filtramos las rifas según el texto de búsqueda (también podría hacerse en BD)
            val mostradas = rifasUI.filter {
                it.nombre.contains(searchText, ignoreCase = true) || // Coincide con el nombre (ignorando mayúsculas)
                        it.fecha.contains(searchText)                // Coincide con la fecha
            }

            // Mostramos cada rifa en una fila
            items(items = mostradas) { rifa: RifaUI ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(rifa.nombre, Modifier.weight(1f), fontSize = 14.sp)
                    Text(rifa.inscritos.toString(), Modifier.weight(1f), fontSize = 14.sp)
                    Text(rifa.fecha, Modifier.weight(1f), fontSize = 14.sp)

                    // Botón para ver detalles del talonario
                    IconButton(
                        onClick = { Talonario(rifa.nombre) }, // Llama la función con el nombre de la rifa
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Ver detalles")
                    }
                }
                Divider(thickness = 0.5.dp) // Línea separadora entre rifas
            }
        }

        Spacer(Modifier.height(16.dp)) // Espacio final

        // Botón centrado para crear una nueva rifa
        Button(
            onClick = { NuevaRifa() },
            modifier = Modifier
                .fillMaxWidth(0.5f)               // Ocupa el 50% del ancho
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Nueva")
        }
    }
}

// Clase de datos que representa el modelo visual de una rifa en la UI
data class RifaUI(
    val nombre: String,     // Nombre de la rifa
    val inscritos: Int,     // Número de inscritos (boletas activas)
    val fecha: String       // Fecha de la rifa en formato texto
)
